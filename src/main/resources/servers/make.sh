#!/bin/bash
set -e

if [ ! -d "env" ]; then
  echo "Creating env"
  python3 -m venv env
  source env/bin/activate
  echo "Installing dependencies"
  pip install beautifulsoup4
  pip install iso3166
  pip install requests
fi

echo "Activating env"
source env/bin/activate
echo "Creating serverlist"
python3 make_serverlist.py
echo "Done!"
