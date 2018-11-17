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
r.connect( {host: '192.168.0.2', port: 28015}, function(err, conn) {
    if (err) throw err;
    connection = conn;
    connection.use("pi");
});

io.on('connection', (socket) => {
    socket.on('create_sensor', (data) => {
        // console.log(data);
        r.table('sensor').insert(data).run(connection, (err, result)=>{
            //socket.emit('sensor_created', result);
        });
    });

    r.table('sensor').changes().run(connection, function(err, cursor) {
        cursor.each((err, row) => {
            console.log(row.new_val);
            socket.emit('sensor_created', row.new_val);
        });
        
    });

    socket.on("get_all", ()=>{
        r.table('sensor').run(connection, (err, cursor)=>{
            if (err) throw err;
            cursor.toArray(function(err, result) {
                if (err) throw err;
                //console.log(JSON.stringify(result, null, 2));
                socket.emit('take_all', JSON.stringify(result));
            });
        });
    });
});



server.listen(3000, '0.0.0.0', function(){
  console.log(`Server running on ${server.address().address} on port ${server.address().port}`);
});