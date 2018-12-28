# P2P File Transfer

## Introduction
peer-to-peer file transfering with `UDP` protocol. Receiver request for a file by its `name` if any Server peers have ready for send file with name of `name` and the valid `path` the file start to transfer.

## Usage
You run the same application for both receiver and trasfer but with diffrent arguments. 
```
p2p -help : show this help
p2p -receive [name] : Receiver request for file [name]
p2p –serve -name [name] -path [path] -port [port]: Senders provide file [name] that is under [path] and send it with port [port]
```
> Note: Senders peer should be run before receiver peers.
