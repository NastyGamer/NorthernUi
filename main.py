#!env/bin/python3
import json
import threading
from time import sleep

import notify2
from dearpygui.core import *
from dearpygui.simple import *

from client import is_connected, connect, disconnect
from make_serverlist import download_servers, is_expired
from servers import Servers

servers = Servers(json.load(open("servers.json", "r")))


def check_status():
    while True:
        set_value("info", "Connected" if is_connected() else "Disconnected")
        sleep(3)


def on_disconnect_pressed(sender, data):
    disconnect()


def on_new_server_pressed(sender, data):
    connect(servers.get(servers.countries()[int(get_value(sender))]))


def on_server_select(sender):
    server = servers.get(servers.countries()[int(get_value(sender))])
    notify2.Notification(f"Connecting to {server.name}").show()
    connect(server)


if __name__ == "__main__":
    notify2.init("NorthernUi")
    if is_expired():
        notify2.Notification("Refreshing servers. Hang on.").show()
        download_servers()
    set_main_window_size(600, 800)
    set_global_font_scale(1.5)
    set_theme("Dark")
    set_main_window_resizable(False)
    set_main_window_title("NorthernUi")
    with window("Northern Ui", width=600, height=800, no_resize=True, no_collapse=True, no_move=True,
                no_title_bar=True, x_pos=0, y_pos=0):
        add_listbox("countries", items=servers.countries(), width=600, num_items=30, callback=on_server_select)
        add_separator()
        add_button("disconnect", callback=on_disconnect_pressed)
        add_same_line()
        add_button("new Server")
        add_same_line()
        add_text("info")
    threading.Thread(target=check_status, args=(), daemon=True).start()
    start_dearpygui()
