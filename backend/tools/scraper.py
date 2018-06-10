# -*- coding: utf-8 -*-

# impostare PYTHONIOENCODING="UTF-8"

from requests import get
from requests.exceptions import RequestException
from contextlib import closing
from bs4 import BeautifulSoup

def parselocal():
    with open('outfile.txt', 'r') as myfile:
        data = myfile.read()

    return parse(data)

def parse(data):
    html = BeautifulSoup(data, 'html.parser')
    name = html.select('.bandablutitolo')[0].text

    trs = html.select('tr')
    minerals = remove_whitespace([x.text for x in trs if (interesting(x.text))])

    water = mparse(minerals)
    water['name'] = name.strip()

    return water

def scrapeAll():
    with open('waters.csv', 'w+') as myfile:
        myfile.write('id;name;ca;mg;na;so4;cl;hco3\n')

    for id in range(540):
        write(id)


def write(id):
    with open('waters.csv', 'a+') as myfile:
        html = simple_get('http://www.acqueinbottiglia.fondazioneamga.org/ricerca_det.asp?ID='+str(id))
        if html is not None:
            w = parse(html)
            waterStr = (str(id)+';'
                     + w['name'] + ';'
                     + str(w['ca']  ) + ';'
                     + str(w['mg']  ) + ';'
                     + str(w['na']  ) + ';'
                     + str(w['so4'] ) + ';'
                     + str(w['cl']  ) + ';'
                     + str(w['hco3']) + ';')
            print(waterStr)
            myfile.write(waterStr+'\n')


def mparse(minerals):
    # rimuove spazi avanti e dietro
    clean = [[x.strip() for x in m.split(':')] for m in minerals]
    res = {}
    for x in clean:
        name = translate_name(x[0])
        if name is not None:
            res[name] = parseNum(x[1])
    #dicts = [{translate_name(x[0]): parseNum(x[1])} for x in clean]

    return res


def parseNum(val):
    if val.lower() == 'n.d.':
        return -1
    else:
        return float(val.replace(',', '.'))

def translate_name(name):
    if 'Mg++' in name:
        return 'mg'
    elif 'Ca++' in name:
        return 'ca'
    elif 'Na+' in name:
        return 'na'
    elif 'HCO' in name:
        return 'hco3'
    elif 'Solfat' in name:
        return 'so4'
    elif 'Clorur' in name:
        return 'cl'
    else:
        print('Nessun valore per chiave ' + name)
        return None


def remove_whitespace(strings):
    return [" ".join(x.split()) for x in strings]

def interesting(text):
    salts = ['Mg++', 'Ca++', 'Na+', 'HCO', 'Solfato', 'Cloruro']
    return any(salt in text for salt in salts)

def save(text, filename):
    with open(filename, "w") as text_file:
        text_file.write(text)

def simple_get(url):
    """
    Attempts to get the content at `url` by making an HTTP GET request.
    If the content-type of response is some kind of HTML/XML, return the
    text content, otherwise return None
    """
    try:
        with closing(get(url, stream=True)) as resp:
            if is_good_response(resp):
                return resp.text
            else:
                return None

    except RequestException as e:
        log_error('Error during requests to {0} : {1}'.format(url, str(e)))
        return None


def is_good_response(resp):
    """
    Returns true if the response seems to be HTML, false otherwise
    """
    content_type = resp.headers['Content-Type'].lower()
    return (resp.status_code == 200 
            and content_type is not None 
            and content_type.find('html') > -1)


def log_error(e):
    """
    It is always a good idea to log errors. 
    This function just prints them, but you can
    make it do anything.
    """
    print(e)

def main():
    scrapeAll()

if __name__ == "__main__":
    main()
