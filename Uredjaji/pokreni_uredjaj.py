import requests
import time
import argparse
import random
import datetime
import yaml
import os
import base64
from cryptography.hazmat.primitives import padding
from cryptography.fernet import Fernet

# Key for symmetric encryption (replace with your own secure key)
encryption_key = b'zZMFXqSbLrWvHaMzvF6zlQRTtg9bXuHMKwsX2MlYnIc='

url = "https://localhost:8443/api/logs/addNew"

def pad_data(data):
    padder = padding.PKCS7(128).padder()
    padded_data = padder.update(data.encode()) + padder.finalize()
    return padded_data

def encrypt_data(data):
    data_str = str(data)
    f = Fernet(encryption_key)
    encrypted_data = f.encrypt(data_str.encode())
    return encrypted_data

def send_post_request(data):
    encrypted_data = encrypt_data(data)
    response = requests.post(url, data=encrypted_data, verify=False)
    print("Status code:", response.status_code)

def send_requests(home, device, interval,switch):
    data = {
        'houseID':home,
        'deviceID':device,
        'exactTime':None,
        'receivedValue':0
    }
    r = 0
    while True:
        if switch:
            r = random.choice([0,1])
        else:
            r = random.uniform(0,40)
        timestamp = time.time()
        current_datetime = datetime.datetime.fromtimestamp(timestamp)
        formatted_datetime = current_datetime.strftime('%Y-%m-%dT%H:%M:%S')
        data['exactTime'] = formatted_datetime
        data['receivedValue'] = r
        send_post_request(data) 
        time.sleep(interval)

# Kreiranje parsera argumenata
parser = argparse.ArgumentParser(description='HTTP POST zahtevi skripta')
parser.add_argument('device', type=str, help='Uredjaj')

# Parsiranje argumenata sa komandne linije
args = parser.parse_args()

# Učitavanje YML datoteke
with open("./konfiguracije/uredjaj_"+args.device+".yml", "r") as f:
    yml_data = yaml.safe_load(f)

# Prikazivanje učitanih podataka
device = yml_data["uredjaj"]

# key = Fernet.generate_key()
# print(key)
# print()

# Pozivanje funkcije za slanje zahteva sa unetim vrednostima
send_requests(device['home'],device['device'], device['interval'],device['onof'])
