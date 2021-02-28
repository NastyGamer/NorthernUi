from random import choice
from typing import List


class Server:
    def __init__(self, jo):
        self.country: str = jo["country"]
        self.link: str = jo["link"]
        self.name: str = jo["name"]


class Servers:
    def __init__(self, jo):
        self.creationDate: str = jo["creationDate"]
        self.servers: List[Server] = [Server(i) for i in jo["countries"]]

    def get(self, country: str) -> Server:
        country_servers: List[Server] = [s for s in self.servers if s.country == country]
        return choice(country_servers)

    def countries(self) -> List[str]:
        l: List[str] = list(set([s.country for s in self.servers]))
        l.sort()
        return l
