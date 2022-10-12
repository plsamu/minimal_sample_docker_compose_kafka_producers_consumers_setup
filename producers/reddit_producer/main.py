#!/usr/bin/python3
import json
import os
import signal
import sys
import time
import traceback

import requests
from kafka import KafkaProducer

from mlog import warn, info, error

"""
https://www.reddit.com/.json
"""

SLEEP_AMOUNT = 300
KAFKA_TOPIC = "topic1"
KAFKA_IP = os.getenv("KAFKA_IP") or "localhost"
KAFKA_PORT = os.getenv("KAFKA_PORT") or "9092"
BOOTSTRAP_SERVERS = "{}:{}".format(KAFKA_IP, KAFKA_PORT)
info(BOOTSTRAP_SERVERS)


def json_serializer(data):
    return json.dumps(data).encode("utf-8")


producer = KafkaProducer(
    bootstrap_servers=[BOOTSTRAP_SERVERS], value_serializer=json_serializer
)

oldmodhash = ""


def get_reddit_data_from_file():
    f = open("index.json")
    return json.load(f)


def get_reddit_data_from_home():
    print("get_reddit_data_from_home")
    return requests.get("https://www.reddit.com/.json").json()


def send_data(data):
    global oldmodhash
    try:
        modhash = data["modhash"]
        oldmodhash = modhash
        children = data["children"]
        for c in children:
            producer.send(
                KAFKA_TOPIC, {"title": c["data"]["title"], "source": c["data"]["url"]}
            )
        producer.flush()
        producer.close()
        print("logs sended")
    except Exception:
        error_message = traceback.format_exc()
        warn(error_message)
        # traceback.print_exc()


def get_reddit_data(mjson):
    print("get_reddit_data")
    info(mjson)
    global oldmodhash
    if "error" in mjson and mjson["error"] == 429:
        warn(f"{mjson['message']}")
        warn(f"error code: {mjson['error']}")
        return
    elif "data" in mjson:
        if mjson["data"]["modhash"] == oldmodhash:
            return
        else:
            return send_data(mjson["data"])
    else:
        error("Unexpected error")
        error(mjson)


def signal_handler(sig, frame):
    print("SIGINT or Ctrl+C detected!")
    sys.exit(0)


if __name__ == "__main__":
    print("reddit producer start")
    signal.signal(signal.SIGINT, signal_handler)
    number_of_reading = 0
    while 1 == 1:
        file_log = open("reddit_producer_logs.txt", "a")
        file_log.write("read number {}\n".format(number_of_reading))
        file_log.close()
        number_of_reading += 1
        data = get_reddit_data_from_home()
        get_reddit_data(data)
        print("sleeping {} seconds".format(SLEEP_AMOUNT))
        time.sleep(SLEEP_AMOUNT)  # it's in seconds, aka 300 secs === 5 mins
