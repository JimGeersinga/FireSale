# FireSale

[![Build Status](https://travis-ci.com/mikevanl/FireSale.svg?branch=main)](https://travis-ci.com/mikevanl/FireSale)

## Gettings started
### Database gegevens
Om FireSale te gebruiken moeten de databaseinstellingen in de omgevingsvariabelen worden opgenomen. De gegeven waarden zijn als voorbeeld, de juiste waarden zijn op aanvraag te verkrijgen.

Name | Value
--- | ---
`FIRESALE_HOST` | `jdbc:mariadb://sql.hosted.hro.nl/0999999?username=0999999&password=Welkom01`
`FIRESALE_PASSWORD` | `Welkom01`
`FIRESALE_USER` | `0999999`

### E-mailinstelligen
Om de wachtwoord-vergeten functionaliteit te kunnen gebruiken moet je e-mailinstellingen in je omgevingsvariabelen opnemen. Voorbeeldgegevens om je schoolaccount te gebruiken:

Name | Value
--- | ---
`FIRESALE_MAILSERVER_HOST` | `outlook.office365.com`
`FIRESALE_MAILSERVER_USER` | `0999999@hr.nl`
`FIRESALE_MAILSERVER_PASS` | `Welkom01`
`FIRESALE_MAILSERVER_FROM` | `FireSale <0999999@hr.nl>`


Voorbeeldgegevens om je Gmail-account te gebruiken. Wil je je Gmail account gebruiken, dan moet je een zogenaamd app-wachtwoord genereren en dat gebruiken als wachtwoord: https://myaccount.google.com/apppasswords

Name | Value
--- | ---
`FIRESALE_MAILSERVER_HOST` | `smtp.gmail.com`
`FIRESALE_MAILSERVER_USER` | `janjansen@gmail.com`
`FIRESALE_MAILSERVER_PASS` | `p@ssw0rd`
`FIRESALE_MAILSERVER_FROM` | `FireSale <janjansen@gmail.com>`

Let in elk geval op dat je altijd bij from het adres gebruikt wat bij de username hoort (meestal is het hetzelfde, maar niet altijd)

## Definition of done
### Server:
- De services die betrekking op deze feature hebben minimaal een falende en een succesvolle unit test hebben
- Voor de repositories is minimaal voor elke methode die gebruikt wordt een Unit test geschreven.
- De endpoints op de server zijn gecontroleerd (d.m.v. Postman o.i.d.)
- De output van de endpoints is gecontroleerd (Inclusief hetgeen dat naar de database is
  geschreven).
- Een Pullrequest is approved door minimaal 1 codeowner.
- Als het een bestaand endpoint betreft zijn in de client alle aanroepende componenten
getest.

### Client:
- De schermen goed renderen.
- De console in de browser geen fouten meer weergeven
- Als het een feature in een component betreft is elke pagina getest waarop dit
component wordt getoond (denk aan modals etc.).
- Als het de server aanspreekt is de output van de client gecontroleerd op de server.
- Waar mogelijk is er een Unit test geschreven voor de logica in de UI
### Shared:
- Er is overlegd met minimaal één teamgenoot (kan door middel van een pullrequest) over hoe de implementatie is gerealiseerd.
- De code volgt de Coding standards zoals deze zijn gehanteerd in de colleges
  - [Googles java guidelines komen goed overeen](https://google.github.io/styleguide/javaguide.html)
  - Voor de rest volgt KIS (Keep it simple), zorg dat je functies makkelijk te begrijpen zijn. 
- Er staat geen code in die je niet regel voor regel begrijpt (geen copy/paste werk van
stackoverflow als je niet begrijpt wat hier staat)
- Je code is encapsulated voor jouw feature en zou geen andere features moeten treffen
ofwel die andere features zijn aangepast zodat deze blijven werken.
- De checklists in de issue zijn achteraf nog gecontroleerd op volledigheid tegen je code
en bij vragen is hierover overleg geweest met een codeowner en is de checklist aangepast 
