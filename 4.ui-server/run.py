#! /usr/bin/env python
from uiserver import socketio, app
socketio.run(app, '0.0.0.0')
