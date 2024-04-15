# PinActivator

PinActivator is a Java application that facilitates the activation of PIN terminals for customers. It communicates with a southbound system to activate PIN terminals based on customer IDs and MAC addresses.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)

## Introduction

PinActivator is designed to streamline the process of activating PIN terminals for customers. It interacts with a southbound system using RESTful APIs to send activation requests and receive responses. This README guides how to install, configure, and use PinActivator effectively.

## Features

- Activate PIN terminals for customers using customer IDs and MAC addresses.
- Communicate with a southbound system to send activation requests and receive responses.
- Handle various response scenarios, including success, not found, and conflict.

## Installation

To install PinActivator, follow these steps:

1. Clone the repository to your local machine.
   ```bash
   git clone https://github.com/your-username/pinactivator.git
2. Navigate to the project directory.
   ```bash
   cd pinactivator
3. Build the project using Maven.
   ```bash
   mvn clean package
5. Run the application.
   ```bash
   java -Dserver.port=(PORT1) -Dsouthboundport=(PORT2) -jar target/pin-activator-0.0.1-SNAPSHOT.jar

## Usage
To use PinActivator: Send post request to : localhost:PORT1/pin-activation with body {"customerId": "12345", "macAddress": "AA:BB:CC:DD:EE:FF"}
