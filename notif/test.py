import re
import requests
from bs4 import BeautifulSoup


page = requests.get('http://www.koeri.boun.edu.tr/scripts/lst0.asp')

# Create a BeautifulSoup object
soup = BeautifulSoup(page.text, 'html.parser')
data = soup.find('pre')

x = str(data).split()
print(x)

