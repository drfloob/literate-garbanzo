#! /usr/bin/env python
from flask import Flask, render_template
from flask_socketio import SocketIO, emit
import rethinkdb as r

app = Flask(__name__)
socketio = SocketIO(app)

def bg_rethink():
    r.connect("172.31.0.12", 28015, db="pulse").repl()
    ccCursor = r.table("clustered_components").changes().run()
    for cc in ccCursor:
        print('got cc')
        socketio.emit('components', {'data': cc}, json=True)
        socketio.sleep(0.2)

thread = None
@socketio.on('connect')
def connected():
    print('connected')
    global thread
    if thread is None:
        thread = socketio.start_background_task(target=bg_rethink)

@socketio.on('disconnect')
def disconnected():
    print('disconnected')    
    
@app.route('/')
def hello():
    return render_template("main.html")

if __name__ == "__main__":
    socketio.run(app, host='0.0.0.0')

