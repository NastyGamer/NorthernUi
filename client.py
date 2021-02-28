import os
import subprocess
from typing import Optional

import requests

from servers import Server


def download_file(server: Server) -> str:
    with open(f"/tmp/{server.name}", "wb") as f:
        f.write(requests.get(server.link).content)
    return f"/tmp/{server.name}"


def connect(server: Server):
    os.system(f"gnome-terminal --wait --window -- openvpn3 session-start --config {download_file(server)}")


def get_session_path() -> Optional[str]:
    try:
        return subprocess.check_output(["openvpn3", "sessions-list"]).decode("UTF8").split("\n")[1].split(": ")[1]
    except:
        return None


def is_connected() -> bool:
    return get_session_path() is not None


def disconnect():
    print(subprocess.check_output(["openvpn3", "session-manage", "--disconnect", "-o", get_session_path()]).decode(
        "UTF8"))
