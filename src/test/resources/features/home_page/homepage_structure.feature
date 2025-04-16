Feature: Front Page Structure

  Scenario Outline: User opens the front page
    Given the user opens the front page
    When user checks the functions panel on the left side
    Then the panel should contains features <feature> in correct order <order>
    Examples:

      | feature           | order |
      | "Przegląd"        | 0     |
      | "Przychody"       | 1     |
      | "Stałe wydatki"   | 2     |
      | "Bieżące wydatki" | 3     |
      | "Skarbonka"       | 4     |
      | "Bilans"          | 5     |
      | "Kalendarz"       | 6     |