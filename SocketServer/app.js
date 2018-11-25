const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const http = require('http');
const r = require('rethinkdb');

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(cors());

const server = http.createServer(app);
const io = require('socket.io').listen(server);

app.get('/', function(req, res){
    console.log('Hello World');
    res.send('Hello World');
});

var connection = null;
r.connect( {host: '192.168.0.108', port: 28015}, function(err, conn) {
    if (err) throw err;
    connection = conn;
    connection.use("pi");
});

io.on('connection', (socket) => {

    // create bin from data
    socket.on('create_sensor', (data) => {
        r.table('bin').insert(data).run(connection, (err, result) => {
            if(result.inserted == 1)
                socket.emit('sensor_created', "Bin Created");
        });
    });

    // update bin data
    socket.on('update_sensor', (id, data) => {
        r.table('bin').get(id).update(data).run(connection, (err, result) => {

        });
    });

    // update bin status
    socket.on('update_bin_status', (id, status) => {
        r.table('bin').get(id).update({'status' : status}).run(connection, (err, result) => {

        });
    });

    // check if the bins are changed
    r.table('bin').changes().run(connection, function(err, cursor) {
        cursor.each((err, row) => {
            // console.log(row.new_val);
            // socket.emit('sensor_created', row.new_val);
        });
    });

    // get all the bins
    socket.on("get_all", ()=>{
        r.table('bin').run(connection, (err, cursor)=>{
            if (err) throw err;
            cursor.toArray(function(err, result) {
                if (err) throw err;
                socket.emit('take_all', result);
            });
        });
    });

    socket.on('get_bin', (id) => {
        r.table('bin').get(id).run(connection, (err, result) => {
            socket.emit('take_bin', result)
        });
    });
});

server.listen(3000, '0.0.0.0', function(){
  console.log(`Server running on ${server.address().address} on port ${server.address().port}`);
});