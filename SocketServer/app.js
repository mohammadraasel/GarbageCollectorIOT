const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const http = require('http');
const r = require('rethinkdb');

const accountSid = 'AC43f2e70a926835350757b73d4ac6e3b4';
const authToken = '12d647fb9f7e5779626fb855f99a0b32';
const client = require('twilio')(accountSid, authToken);

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(cors());

const server = http.createServer(app);
const io = require('socket.io').listen(server);

let send_sms_remember = []

app.get('/', function (req, res) {
    console.log('Hello World');
    res.send('Hello World');
});

var connection = null;
r.connect({
    host: '192.168.0.108',
    port: 28015
}, function (err, conn) {
    if (err) throw err;
    connection = conn;
    connection.use("pi");
});

io.on('connection', (socket) => {

    // create bin from data
    socket.on('create_sensor', (data) => {
        r.table('bin').insert(data).run(connection, (err, result) => {
            if (result.inserted == 1)
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
        r.table('bin').get(id).update({
            'status': status
        }).run(connection, (err, result) => {
            if (result.replaced == 1) {
                socket.emit('bin_status_updated', "Status Changed to " + status);
            }
        });
    });

    // tune bin now
    socket.on('tune_bin', (id, data) => {
        r.table('bin').get(id).update(data).run(connection, (err, result) => {});
    });

    // check if the bins are changed
    r.table('bin').changes().run(connection, function (err, cursor) {
        cursor.each((err, row) => {
            if (row.new_val != null && row.old_val != null) {
                socket.emit('update_current_level', row.new_val);
                if (row.new_val.current_level >= row.new_val.notify_level) { // if current level is greater than notify level send a sms
                    // if sms not sent before sent now and add it in remember list
                    if (!send_sms_remember.includes(row.new_val.name)) {
                        // sending sms
                        send_sms(`Bin ${row.new_val.name} is almost full, Please clean this now`, "+19386665994", "+8801841714244")
                        // remembering sent sms
                        send_sms_remember.push(row.new_val.name)
                    }
                } else if (row.new_val.current_level < 5) { // If bin level less then 5 then sms service will reset and ready to send sms
                    // Checking if bin sent a sms
                    let index = send_sms_remember.indexOf(row.new_val.name);
                    // if bin sent a sms before now remove to rest it
                    if (index > -1) {
                        // removing the bin from remember list
                        send_sms_remember.splice(index, 1)
                    }
                }
            }

        });
    });

    // get all the bins
    socket.on("get_all", () => {
        r.table('bin').run(connection, (err, cursor) => {
            if (err) throw err;
            cursor.toArray(function (err, result) {
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

let send_sms = (msg, send_from, send_to) => {
    client.messages
        .create({
            body: msg,
            from: send_from,
            to: send_to
        })
        .then(message => console.log(message.sid))
        .done();
}

server.listen(3000, '0.0.0.0', function () {
    console.log(`Server running on ${server.address().address} on port ${server.address().port}`);
});