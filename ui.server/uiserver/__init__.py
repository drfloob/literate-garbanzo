#! /usr/bin/env python
from flask import Flask, render_template
from flask_socketio import SocketIO, emit
from kafka import KafkaConsumer

timeout = 700
consumer = KafkaConsumer("gh_components", bootstrap_servers='172.31.0.10:9092,172.31.0.8:9092,172.31.0.6:9092,172.31.0.7:9092',
                         group_id="flasky", client_id="flasky", api_version=(0,8,2),
                         fetch_max_wait_ms=timeout, request_timeout_ms=timeout, consumer_timeout_ms=timeout)

import collections
NullRecord = collections.namedtuple('NullRecord', ['value'])

app = Flask(__name__)
socketio = SocketIO(app)

@socketio.on('my event')
def echo_socket(msg):
    emit('response', {'data': 'you said: ' + msg['data']})
            
@socketio.on('get cc')
def connect_push():
    print("connected for graph")
    val = next(consumer, NullRecord(b'[]'))
    print("next consumer retrieved/mocked")
    emit('components', {'data': val.value.decode('utf-8').replace("=", ":")})
    print("sent graph")

@socketio.on('connection')
def connected():
    print('connected')

@socketio.on('disconnect')
def disconnected():
    print('disconnected')    
    
@app.route('/')
def hello():
    return render_template("main.html")

if __name__ == "__main__":
    # from uiserver import socketio, app
    socketio.run(app, host='0.0.0.0')

