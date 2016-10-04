from setuptools import setup

setup(
    name='ui.server',
    version='1.0',
    long_description=__doc__,
    packages=['uiserver'],
    include_package_data=True,
    zip_safe=False,
    install_requires=['Flask', 'eventlet', 'flask_socketio', 'rethinkdb']
)
