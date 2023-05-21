import requests
import time
import argparse
import random
import datetime
import yaml

url = "http://localhost:8080/api/logs/addNew"


def send_post_request(data):
    print(data)
    response = requests.post(url, json=data)
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
        send_post_request(data)  # Promenite podatke koji se šalju
        time.sleep(interval)

# Kreiranje parsera argumenata
parser = argparse.ArgumentParser(description='HTTP POST zahtevi skripta')
# parser.add_argument('home', type=str, help='Kuca za koju se salje')
# parser.add_argument('device', type=str, help='Uredjaj koji salje')
# parser.add_argument('interval', type=int, help='Vremenski interval između zahteva (u sekundama)')
# parser.add_argument('onof', type=int, help='Vrednost ili true/false')
parser.add_argument('device', type=str, help='Uredjaj')

# Parsiranje argumenata sa komandne linije
args = parser.parse_args()

# Učitavanje YML datoteke
with open("./konfiguracije/uredjaj_"+args.device+".yml", "r") as f:
    yml_data = yaml.safe_load(f)

# Prikazivanje učitanih podataka
device = yml_data["uredjaj"]
# print(device)

# Pozivanje funkcije za slanje zahteva sa unetim vrednostima
send_requests(device['home'],device['device'], device['interval'],device['onof'])
