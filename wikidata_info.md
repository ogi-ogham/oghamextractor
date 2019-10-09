# Wikidata Ogham

## general

| TERM | WIKIDATA |
|------|----------|
| Ogham | https://www.wikidata.org/wiki/Q184661 |
| Ogham Letter | https://www.wikidata.org/wiki/Q41812345 |
| Ogham Word | https://www.wikidata.org/wiki/Q67384733 |
| Ogham formular word | https://www.wikidata.org/wiki/Q67381377 |
| Ogham nomenclature word | https://www.wikidata.org/wiki/Q67382150 |
| Ogham Stone  | https://www.wikidata.org/wiki/Q2016147 |

## formular words

| WORD | WIKIDATA |
|------|----------|
| MAQI | https://www.wikidata.org/wiki/Q67381254 |
| MUCOI | https://www.wikidata.org/wiki/Q67999759 |
| ANM | https://www.wikidata.org/wiki/Q68000035 |
| AVI | https://www.wikidata.org/wiki/Q68000199 |
| CELI | https://www.wikidata.org/wiki/Q68000377 |
| KOI | https://www.wikidata.org/wiki/Q68000462 |

```
SELECT * WHERE {
  ?item wdt:P31 wd:Q67381377.
  OPTIONAL {
	?item rdfs:label ?label.
	FILTER(LANG(?label) = "en").
  }
}
```

## nomenclature words

| WORD | WIKIDATA |
|------|----------|
| CUNA | https://www.wikidata.org/wiki/Q67382235 |
| CATTU | https://www.wikidata.org/wiki/Q67383338 |
| LUG | https://www.wikidata.org/wiki/Q67383482 |
| ERC | https://www.wikidata.org/wiki/Q67382360 |
| DALAGNI | https://www.wikidata.org/wiki/Q68001812 |
| DERCMASOC | https://www.wikidata.org/wiki/Q68001907 |

```
SELECT * WHERE {
  ?item wdt:P31 wd:Q67382150.
  OPTIONAL {
    ?item rdfs:label ?label.
    FILTER(LANG(?label) = "en").
  }
}
```

## alphabet / letters

| WORD | WIKIDATA |
|------|----------|
| Beith | https://www.wikidata.org/wiki/Q4881483 |

```
SELECT * WHERE {
  ?item wdt:P31 wd:Q41812345.
  OPTIONAL {
    ?item rdfs:label ?label.
    FILTER(LANG(?label) = "en").
  }
} ORDER BY ?label
```

## Stones in Ireland

| Stone | WIKIDATA |
|-------|----------|
| CIIC 38 | https://www.wikidata.org/wiki/Q67510124 |
| CIIC 154 | https://www.wikidata.org/wiki/Q68002826 |
| CIIC 203 | https://www.wikidata.org/wiki/Q67978531 |

### Stones at University College Cork (UCC)

| Stone | UCC No. | WIKIDATA |
|-------|---------|----------|
| CIIC 103 | 1 | https://www.wikidata.org/wiki/Q69377850 |
| CIIC 115 | 2 | https://www.wikidata.org/wiki/Q69383434 |
| CIIC 117 | 3 | https://www.wikidata.org/wiki/Q69385241 |
| CIIC 81 | 4 | https://www.wikidata.org/wiki/Q69385424 |

### Stones in Scotland

| Stone | WIKIDATA |
|-------|----------|
| CIIC 506 | https://www.wikidata.org/wiki/Q69386261 |

### Stones at the Isle of Man

| Stone | WIKIDATA |
|-------|----------|
| CIIC 503 | https://www.wikidata.org/wiki/Q69388030 |
| CIIC 504 | https://www.wikidata.org/wiki/Q69388229 |

### Stones in England

| Stone | WIKIDATA |
|-------|----------|
| CIIC 489 | https://www.wikidata.org/wiki/Q69389090 |

### Stones in Wales

| Stone | WIKIDATA |
|-------|----------|
| CIIC 426 | https://www.wikidata.org/wiki/Q69389329 |

### SPARQL queries

**all stones and collections**

> https://w.wiki/9B9

```
SELECT * WHERE {
  ?item wdt:P31 wd:Q2016147;
    wdt:P361 wd:Q67978809;
    wdt:P195 ?collection.
  OPTIONAL { ?item wdt:P625 ?geo. }
  OPTIONAL {
    ?item rdfs:label ?label.
    FILTER((LANG(?label)) = "en")
  }
  OPTIONAL {
    ?collection rdfs:label ?collectionLabel.
    FILTER((LANG(?collectionLabel)) = "en")
  }
}
ORDER BY (?label)
```

**stones at UCC**

> https://w.wiki/9B8

```
SELECT * WHERE {
  ?item wdt:P31 wd:Q2016147;
    wdt:P361 wd:Q67978809;
    wdt:P195 wd:Q69379477.
  OPTIONAL { ?item wdt:P625 ?geo. }
  OPTIONAL {
    ?item rdfs:label ?label.
    FILTER((LANG(?label)) = "en")
  }
}
ORDER BY (?label)
```

## Added Townlands

| Townland | WIKIDATA |
|-------|----------|
| Barrahaurin | https://www.wikidata.org/wiki/Q69378412 |
| Ballyboodan | https://www.wikidata.org/wiki/Q69378747 |
| Ballinrannig | https://www.wikidata.org/wiki/Q69379307 |
| Coolmagort| https://www.wikidata.org/wiki/Q69379704 |
| Knockshanawee | https://www.wikidata.org/wiki/Q69383268 |
| Garranes | https://www.wikidata.org/wiki/Q69385525 |

> townlands: https://www.townlands.ie/

## Addes other Places

| Townland | WIKIDATA |
|-------|----------|
| Ballaqueeny | https://www.wikidata.org/wiki/Q69387717 |

## Collections

| Collection | WIKIDATA |
|-------|----------|
| Ogham Stones w/o collection reference | https://www.wikidata.org/wiki/Q69387988 |
| Stone Corridor University College Cork | https://www.wikidata.org/wiki/Q69379477 |
| Ballinrannig Ogham Stones | https://www.wikidata.org/wiki/Q69379601 |
| Coolmagort Ogham Stones | https://www.wikidata.org/wiki/Q69379810 |
| Knockshanawee Ogham Stones | https://www.wikidata.org/wiki/Q69383371 |
| Ballaqueeny Ogham Stones | https://www.wikidata.org/wiki/Q69387903 |

## Added Books

| Book | WIKIDATA |
|-------|----------|
| Macalister.Corpus Inscriptionum Insularum Celticarum Vol.1 | https://www.wikidata.org/wiki/Q70256237 |
