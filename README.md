# Currency Map
Currencies fluctuate. You can know what country is more affordable, and go travel there. It is fun to see the world!

## TODO

- [x] Add class with country name to SVG file
- [x] Get map of countryname->currency
- [x] Download 10 years of currency fx data
- [x] Figure out the average of all prices for each currency
- [x] Figure out how to paint one country
- [x] Find delta between current fx rate and historical average
- [x] Map each country to the delta value their currency has
- [ ] Convert that delta to a hex number that corresponds to a color
- [ ] paint every country with a `(fn [medium-value current-value] (delta medium-value current-value))`
- [ ] render! is a fn that paints `.countrycode` with that function

## to run dev server
`$ boot dev`

## Licence
GNU Gpl v3 or higher. Free software
