Feature: Front Page Structure

  Scenario Outline: User opens the front page
    Given the user opens the front page
    When user checks the functions panel on the left side
    Then the panel should contains features <feature> in correct order <order>
    Examples:

      | feature           | order |
      | "Przegląd"        | 0     |
      | "Bieżące wydatki" | 1     |
      | "Bilans"          | 2     |
      | "Przychody"       | 3     |
      | "Stałe wydatki"   | 4     |
      | "Skarbonka"       | 5     |