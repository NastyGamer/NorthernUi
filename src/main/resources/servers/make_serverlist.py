import json
import re
from datetime import date

import requests
from bs4 import BeautifulSoup
from iso3166 import countries

if __name__ == "__main__":
    soup = BeautifulSoup(requests.get("https://nordvpn.com/ovpn/").content, "html.parser")
    aTags = soup.find_all("a", class_="Button Button--primary Button--small")
    hrefs = []
    for aTag in aTags:
        if aTag.get("href").endswith(".ovpn"):
            hrefs.append(aTag.get("href"))
    data = {"creationDate": date.today().strftime("%d.%m.%Y"), "countries": []}
    pattern = re.compile(
        r"https://downloads\.nordcdn\.com/configs/files/ovpn_legacy/servers/(\w{2})(\d{1,4})\.nordvpn\.com\.(udp|tcp)\d{1,4}\.ovpn")
    for href in hrefs:
        try:
            cc = re.search(pattern, href).group(1)
            cn = countries.get(cc).name
            data["countries"].append({"country": cn, "name": cn + "-" + re.search(pattern, href).group(2), "link": href})
        except:
            pass
    with(open("../servers.json", "w+")) as file:
        json.dump(data, file, indent=4)
