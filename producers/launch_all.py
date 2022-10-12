#!/usr/bin/python3

"""
This script will call all the main.py inside all the subdir in this dir
"""

from multiprocessing import Process
import io
import subprocess
import os

file_log = open("producers_logs.txt", "a")
file_log.write("launch all producers")
file_log.close()

dirname = os.path.dirname(__file__)
abs_path = os.path.abspath(dirname)
root, dirs, files = next(os.walk(abs_path))


def spawn_producers(dir):
    mainpath = os.path.join(abs_path, dir, "main.py")
    with subprocess.Popen(
        ["python", "-u", mainpath],
        shell=False,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
    ) as proc:
        print("READING -> {}".format(mainpath))
        for line in io.TextIOWrapper(proc.stdout, encoding="utf-8"):  # type: ignore
            print(line)
        for line in io.TextIOWrapper(proc.stderr, encoding="utf-8"):  # type: ignore
            print(line)


if __name__ == "__main__":
    process = []
    for dir in dirs:
        p = Process(target=spawn_producers, args=(dir,))
        process.append(p)
    for p in process:
        p.start()
