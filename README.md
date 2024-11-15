# StudyBuddy

# Opis projekta

Ovaj projekt je rezultat timskog rada u sklopu projektnog zadatka kolegija Programsko inženjerstvo na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu.

Širenje ovisnosti o digitalnim uređajima dovodi do problema s koncentracijom i smanjene sposobnosti dubokog razmišljanja. To je samo jedan od uzroka otežane organizacije vremena i zadataka, no s njime se susreće velik broj učenika i studenata. Što se tiče akademskih zahtjeva, određena gradiva i predmeti učenicima mogu djelovati odbojno i pretjerano složeno, zbog čega se gubi interes za redovitim radom i svladavanjem gradiva. Također, ne pružaju sve obrazovne ustanove adekvatan način podučavanja, niti podršku svojih nastavnika, što učenicima otežava rješavanje problema na vrijeme i s razumijevanjem.  
Prethodno navedeni problemi pridonose povećanju stresa i anksioznosti zbog akademskih izazova, pritisaka u vezi ispita te društvenih očekivanja i neizvjesnosti u budućnosti. Zbog takvih je problema i nedostataka osmišljena platforma StudyBuddy.

Aplikacija je osmišljena s ciljem brzog povezivanja i koordinacije vršnjaka kojima je namjera zajedničkim učenjem lakše svladati akademske izazove. Želimo da aplikacija služi kao digitalni prostor koji okuplja učenike sličnih ciljeva, promovirajući razvijanje socijalnih vještina kroz interakciju s vršnjacima, te da za profesore predstavlja platformu za širenje dosega svog utjecaja, čime mogu pomoći većem broju učenika koji traže dodatne poduke i objašnjenja.

# Funkcijski zahtjevi

- Platforma ima početnu web-stranicu koja korisnicima omogućuje jednostavan pristup opcijama za registraciju i prijavu.
- Neregistrirani korisnici vide samo početnu stranicu i stranicu za prijavu i registraciju.  
- Korisnicima je omogućena prijava preko njihovog Google računa, ali i upisivanje maila nekog drugog pružatelja mail usluge.   
- Prilikom kreiranja novog računa, korisnici odabiru rade li registraciju kao učenici, studenti ili profesori.  
- Prijavljeni korisnici imaju javne profile koji sadrže osnovne podatke o korisniku, a ti se profili mogu uređivati.   
- Profesori na platformi imaju mogućnost objavljivanja poziva na „masovne instrukcije“ te definiraju detalje vezane uz određeni termin.  
- Učenici i studenti mogu objavljivati termine grupa za učenje te također određuju detalje vezane uz događaj. Oni pretražuju ponuđene oglase te se prijavljuju za pohađanje instrukcija ili za sudjelovanje u grupama za učenje. Također imaju opciju objave recenzija na profilima profesora.  
- Administrator sustava nadzire aktivnost svih korisnika i pregledava pristigle prijave i zahtjeve.   
- Korisnik može privremeno deaktivirati svoj račun pa se tijekom neaktivnosti s tim računom ne može komunicirati. Administrator također može deaktivirati i blokirati neki račun ako je sam uočio neprimjereno ponašanje ili je primio prijavu poslanu od nekog drugog korisnika.  
- Svaki korisnik ima mogućnost komunikacije s ostalim korisnicima razmjenom poruka (čavrljanje).  
- Svi se novi podaci i izmjene postojećih podataka o korisnicima, instrukcijama, grupama, recenzijama i prijavama spremaju u bazu podataka.  


# Tehnologije

## Spring Boot + Maven

### Preduvjeti
#### 1. Java Development Kit (JDK)
Potrebno je JDK imati instaliran na svom računalu. Možete ga preuzeti sa stranice: [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) ili koristite OpenJDK s [ove poveznice](https://jdk.java.net/). 

#### 2. Instalacija Mavena
Ako već nemate Maven instaliran globalno, možete ga instalirati preuzimanjem najnovije verzije s [Apache Maven web stranice](https://maven.apache.org/download.cgi). Nakon preuzimanja, raspakirajte arhivu i slijedite [upute za instalaciju](https://maven.apache.org/install.html) kako biste dodali Maven u `PATH` sustava.
Alternativno, ovaj projekt pruža **Maven Wrapper** (`mvnw`), pa nije potrebno ručno instalirati Maven. 
Možete koristiti tu skriptu za pokretanje Maven naredbi bez globalne instalacije.

### Izgradnja projekta
Za kompilaciju i pakiranje projekta kao JAR, porenite sljedeću naredbu u glavnom direktoriju projekta:
```
./mvnw clean install
```
Nakon izgradnje projekta, možete pokrenuti Spring Boot aplikaciju koristeći Maven Wrapper:
```
./mvnw spring-boot:run
```

Za frontend specifične upute pogledajte na [README](frontend/README.md).

## Docker
Aplikacija se može jednostavno postaviti i pokrenuti korištenjem Docker Compose-a. Prije svega, provjerite imate li instalirane sljedeće alate: - [Docker](https://www.docker.com/get-started) i [Docker Compose](https://docs.docker.com/compose/install/).
U terminalu je potrebno izvršiti naredbu:
```
docker-compose up –build
```
Backend (REST API) je dostupan na: http://localhost:8080.  
Frontend je dostupan na: http://localhost:5173.   

Zaustavljanje aplikacije:  
```
docker-compose down
```


Prije pokretanja aplikacije, provjerite i po potrebi prilagodite application.properties datoteku:
```
spring.application.name=study-buddy
spring.profiles.active=local

spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
#spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

spring.sql.init.mode=always
logging.level.org.springframework.security=DEBUG

spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=admin
spring.datasource.password=admin

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.h2.console.path=/h2-console
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.driverClassName=org.h2.Driver

server.error.include-message=always
server.error.include-binding-errors=always

spring.security.oauth2.client.registration.google.client-id=GOOGLE-CLIENT-ID
spring.security.oauth2.client.registration.google.client-secret=GOOGLE-CLIENT-SECRET
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://accounts.google.com
```


# Članovi tima
> Erik Kranjec  
> Ella Grković  
> Karlo Mezdić  
> Anamarija Kic  
> Tena Osredečki  
> Darian Begović  
> Mila Podrug  


# 📝 Kodeks ponašanja [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)
[KODEKS PONAŠANJA STUDENATA FAKULTETA ELEKTROTEHNIKE I RAČUNARSTVA SVEUČILIŠTA U ZAGREBU](https://www.fer.hr/_download/repository/Kodeks_ponasanja_studenata_FER-a_procisceni_tekst_2016%5B1%5D.pdf)  
Dodatni naputci za timski rad na predmetu: [Programsko inženjerstvo](https://wwww.fer.hr).  
Očekuje se poštivanje [etičkog kodeksa IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html) koji ima važnu obrazovnu funkciju sa svrhom postavljanja najviših standarda integriteta, odgovornog ponašanja i etičkog ponašanja u profesionalnim aktivnosti. Time profesionalna zajednica programskih inženjera definira opća načela koja definiraju moralni karakter, donošenje važnih poslovnih odluka i uspostavljanje jasnih moralnih očekivanja za sve pripadnike zajenice.

Kodeks ponašanja skup je provedivih pravila koja služe za jasnu komunikaciju očekivanja i zahtjeva za rad zajednice/tima. Njime se jasno definiraju obaveze, prava, neprihvatljiva ponašanja te  odgovarajuće posljedice (za razliku od etičkog kodeksa). U ovom repozitoriju dan je jedan od široko prihvačenih kodeksa ponašanja za rad u zajednici otvorenog koda.

>### Poboljšanjee funkcioniranja tima:
>* definiranje načina na koji će rad biti podijeljen među članovima grupe
>* dogovor kako će grupa međusobno komunicirati.
>* ne gubite vrijeme na dogovore na koji će grupa rješavati sporove primjenite standarde!
>* podrazumijeva se da svi članovi grupe slijede kodeks ponašanja.
 
>###  Prijava problema
>Postoji nekoliko stvari koje možete učiniti kako biste najbolje riješili sukobe i probleme:
>* Obratite mi se izravno [e-pošta](mailto:vlado.sruk@fer.hr) i  učinit ćemo sve što je u našoj moći da u punom povjerenju saznamo koje korake trebamo poduzeti kako bismo riješili problem.
>* Razgovarajte s vašim asistentom jer ima najbolji uvid u dinamiku tima. Zajedno ćete saznati kako riješiti sukob i kako izbjeći daljnje utjecaje u vašem radu.
>* Ako se osjećate ugodno neposredno razgovarajte o problemu. Manje incidente trebalo bi rješavati izravno. Odvojite vrijeme i privatno razgovarajte s pogođenim članom tima te vjerujte u iskrenost.

## Licenca

Ovaj projekt je licenciran pod [MIT Licencom](LICENSE).
