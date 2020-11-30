# FireSale

[![Build Status](https://travis-ci.com/mikevanl/FireSale.svg?branch=main)](https://travis-ci.com/mikevanl/FireSale)


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
- Je code is encapsulated voor jou feature en zou geen andere features moeten treffen
ofwel die andere features zijn aangepast zodat deze blijven werken.
- De checklists in de issue zijn achteraf nog gecontroleerd op volledigheid tegen je code
en bij vragen is hierover overleg geweest met een codeowner en is de checklist aangepast
