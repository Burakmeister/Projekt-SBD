INSERT INTO projekt.uzytkownik (imie, nazwisko, nickname, dataZalozeniaKonta, uprawnieniaAdministratora, password, email)
VALUES ('Grzegorz', 'Denert', 'BURAK', '1945-08-09', '1', 'BURAK', 'buraczk@gmail.com');

INSERT INTO projekt.uzytkownik (imie, nazwisko, nickname, dataZalozeniaKonta, uprawnieniaAdministratora, password, email)
VALUES ('Dawid', 'Bartosiuk', 'Grzechosz', '1945-08-09', '0', 'Grzechosz', 'grzegorz@denert.pl');

INSERT INTO projekt.kategoria (nazwaKategorii, opisKategorii)
VALUES ('Procesor', 'Sekwencyjne urządzenie cyfrowe, które pobiera dane z pamięci operacyjnej lub strumienia danych, 
interpretuje je i wykonuje jako rozkazy, zwracając dane do pamięci lub wyjściowego strumienia danych.');

INSERT INTO projekt.kategoria (nazwaKategorii, opisKategorii)
VALUES ('Ram', 'Pamięć o dostępie swobodnym, pamięć główna.');

INSERT INTO projekt.kategoria (nazwaKategorii, opisKategorii)
VALUES ('Zasilacz', 'Urządzenie służące do dopasowania dostępnego napięcia do wymagań zasilanego urządzenia.');

INSERT INTO projekt.kategoria (nazwaKategorii, opisKategorii)
VALUES ('Karta Graficzna', 'Karta rozszerzeń komputera odpowiedzialna za renderowanie grafiki 
i jej konwersję na sygnał zrozumiały dla wyświetlacza.');

INSERT INTO projekt.kategoria (nazwaKategorii, opisKategorii)
VALUES ('Płyta Główna', 'Potocznie określana jako mobo, to najbardziej podstawowa i fundamentalna część komputera.');

INSERT INTO projekt.producent (nazwaProducenta, kraj, opisProducenta)
VALUES ('GOODRAM', 'Polska','Producent pamięci komputerowych marki Goodram mający siedzibę w Łaziskach Górnych.');

INSERT INTO projekt.producent (nazwaProducenta, kraj, opisProducenta)
VALUES ('Kingston', 'Stany Zjednoczone','Amerykańskie przedsiębiorstwo zajmujące się opracowywaniem, 
produkcją, sprzedażą i serwisem pamięci komputerowych.');

INSERT INTO projekt.producent (nazwaProducenta, kraj, opisProducenta)
VALUES ('AMD', 'Stany Zjednoczone','Amerykańskie przedsiębiorstwo produkujące elektronikę (głównie układy scalone) 
dla użytkowników domowych i firm z siedzibą w Santa Clara.');

INSERT INTO projekt.producent (nazwaProducenta, kraj, opisProducenta)
VALUES ('Intel', 'Stany Zjednoczone','Największy na świecie producent układów scalonych oraz twórca mikroprocesorów z rodziny x86, 
które znajdują się w większości komputerów osobistych.');

INSERT INTO projekt.producent (nazwaProducenta, kraj, opisProducenta)
VALUES ('ASUS', 'Tajwan','Tajwańskie przedsiębiorstwo zajmujące się produkcją elektroniki.');

INSERT INTO projekt.sposobrealizacji (sposReal, koszt, czasWysylki, wniesienie)
VALUES ('Kurier INPOST', '19.99','3', '1');

INSERT INTO projekt.sposobrealizacji (sposReal, koszt, czasWysylki, wniesienie)
VALUES ('Kurier Pocztex', '18.99','5', '0');

INSERT INTO projekt.sposobrealizacji (sposReal, koszt, czasWysylki, wniesienie)
VALUES ('Odbiór osobisty', '0','0', '0');

INSERT INTO projekt.adres ( miasto, kodPocztowy, ulica, nrBudynku, nrLokalu)
VALUES ("Suwałki", "16400", "Sokólska", "13", "2");

INSERT INTO projekt.magazyn ( pojemnosc, Adres_idAdres)
VALUES ("6900", "1");

INSERT INTO projekt.zamowienie ( dataGodzina, uwagi, Adres_idAdres, Sposob_Realizacji_idSposob_Realizacji, Uzytkownik_idUzytkownik)
VALUES ( '2022-12-28 14:56:50', 'Brak', '1', '1', '1');

INSERT INTO projekt.produkt (nazwaProduktu, cena, opis, masa, Kategoria_idKategoria, Producent_idProducent, LiczbaSztuk, nazwaObrazka)
VALUES ('Gigabyte 16GB (2x8GB) 3333Mhz CL18 Aorus RGB', '265', 
'Jeżeli potrzebujesz pamięci, która przeniesie Twój komputer na wyższy poziom, zestaw AORUS RGB jest rozwiązaniem dla Ciebie. Moduły te zostały zaprojektowane z myślą o byciu najlepszymi w swojej klasie, oferując doskonałą wydajność oraz unikalny design. Luksusowy aluminiowy radiator o błyszczącym wykończeniu oraz diody LED RGB sprawiają, że moduły DDR4 AORUS wyróżniają się na tle konkurencji.',
 '0.1', '2', '5', '0', 'ram_rgb_niefajny.png');
 
 INSERT INTO projekt.produkt (nazwaProduktu, cena, opis, masa, Kategoria_idKategoria, Producent_idProducent, LiczbaSztuk, nazwaObrazka)
VALUES ( 'Pamięć RAM Corsair Vengeance RGB Pro DDR4 16GB (2 x 8GB) 3200 CL16', '299', 
'Przetaktowana pamięć Corsair Vengeance RGB Pro to najwyższa przepustowość i krótki czas reakcji. Zoptymalizowano je pod kątem maksymalnej wydajności na najnowszych płytach głównych obsługujących procesory Intel oraz AMD. Niestandardowa, wysokowydajna płytka drukowana zapewnia najwyższą jakość sygnału, wydajność i stabilność.
Stylowe podświetlenie RGB poprawi estetykę Twojego komputera, jednocześnie zapewniając wyjątkowo łatwą instalację, nie wymagającą podłączania dodatkowych przewodów.',
 '0.12', '2', '2', '0', 'ram_rgb_bialy.jpg');
 
 INSERT INTO projekt.produkt (nazwaProduktu, cena, opis, masa, Kategoria_idKategoria, Producent_idProducent, LiczbaSztuk, nazwaObrazka)
VALUES ('Pamięć do laptopa GoodRam SODIMM, DDR3, 8 GB, 1333 MHz, CL9', '99.99', 
'Standard pamięci RAM wykonanej w technologii 90 nm lub mniejszej. Umożliwia zastosowanie napięcia 1.5 V. Dzięki temu udało się uzyskać spadek poboru mocy aż o 40% w stosunku do DDR2, a także większą przepustowość. To energooszczędne i relatywnie tanie rozwiązanie, które sprawdzi się przy obsłudze wielu wymagających gier i programów. Po ten standard nadal chętnie sięgają miłośnicy wirtualnej rozrywki, a także użytkownicy profesjonalni, ceniący wysoką wydajność.',
 '0.08', '2', '1', '0', 'ram.jpg');
 
INSERT INTO projekt.produkt (nazwaProduktu, cena, opis, masa, Kategoria_idKategoria, Producent_idProducent, LiczbaSztuk, nazwaObrazka)
VALUES ('ASUS GeForce RTX 3060 DUAL 12GB OC V2', '1799', 
'ASUS GeForce RTX 3060 DUAL 12GB OC V2 to zaawansowana technologicznie karta graficzna, która sprosta oczekiwaniom najbardziej wymagających użytkowników. Nowoczesny design, podświetlenie RGB, unikalne chłodzenie i Ray Tracing sprawiają, że to propozycja godna polecenia wszystkim graczom.',
 '1.1', '4', '5', '0', 'rtx3060.jpg');
 
INSERT INTO projekt.produkt (nazwaProduktu, cena, opis, masa, Kategoria_idKategoria, Producent_idProducent, LiczbaSztuk, nazwaObrazka)
VALUES ('AMD Ryzen 5 3600', '449.59', 
'Procesor AMD Ryzen 5 3600 korzysta z nowatorskiej architektury Zen 2. To dzięki niej CPU osiąga znakomite wyniki w grach i podczas obsługi profesjonalnych aplikacji, utrzymując stale wysoką wydajność. Jednostka posiada 6 rdzeni i 12 wątków, a pamięć cache liczy 35 MB. Rdzenie taktowane są zegarami bazowymi 3,60 GHz, które wzrasta w trybie Turbo do 4,20 GHz i dostarcza jeszcze więcej mocy do pracy oraz rozrywki.',
 '0.3', '1', '3', '0', 'ryzen.png');
 
 