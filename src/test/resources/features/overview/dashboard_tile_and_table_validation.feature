Feature: Validate tile summary matches table data

  Scenario Outline: Tile summary number equals the sum of the column in the table
    Given feature overview page is loaded
    When user note the number displayed on the tile
    And click on the details button <Index>
    Then user is redirected to the <Tile> table page
    And user sum the values in the <Column> column of the table
    Then the sum of the <Index> column should equal the number displayed on the tile
    And the sum of income, recurring-bills and transactions should equals balance-sheet

    Examples:
      | Tile              | Index | Column  |
      | "Przychody"       | 0     | "Kwota" |
      | "Stałe wydatki"   | 1     | "Kwota" |
      | "Bieżące wydatki" | 2     | "Kwota" |