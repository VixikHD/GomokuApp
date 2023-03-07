# Patterns

- Symbols
  - 'P' stands for player's placed symbol
  - 'O' stands for opponent's placed symbol
  - 'X' stands for either opponent's placed symbol or none
  - 'Y' stands for either player's placed symbol or none
  - 'A' stands for any symbol
  - '0-9' where to place next symbol, so currently there's no symbol

# Simple patterns
- Offensive pattern type example

```json
{
  "name": "Closed four in a row",
  "description": "todo",
  "type": "Offensive",
  "patterns": [
    [
      "OPPPP0"
    ],
    [
      "OAAAAA",
      "APAAAA",
      "AAPAAA",
      "AAAPAA",
      "AAAAPA",
      "AAAAA0"
    ]
  ]
}
```

# Merged patterns
- Symbols:
  - All the symbols already mentioned works the same
  - '$' position, where the pattern parts should match