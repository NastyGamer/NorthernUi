#!/bin/bash
set -e

if [ ! -d "env" ]; then
  echo "Creating env"
  python3 -m venv env
  source env/bin/activate
  echo "Installing dependencies"
  pip install beautifulsoup4 iso3166 requests dbus-python dearpygui notify2
fi

echo "Activating env"
source env/bin/activate
echo "Creating serverlist"
python3 main.py
echo "Done!"
