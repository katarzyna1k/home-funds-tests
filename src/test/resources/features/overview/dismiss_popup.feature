Feature: Closing the popup without adding the record

  Scenario Outline:  User closes the popup using Dismiss button, and the table remains unchanged
    Given the user open the <Feature> feature page
    When the table is displayed
    And the user note the initial data in the table
    And the user clicks the Add button in the subpage
    And the popup appears
    And the user fills the following inputs:
      | field    | value    |
      | <field1> | <value1> |
      | <field2> | <value2> |
      | <field3> | <value3> |
      | <field4> | <value4> |
    When the user clicks the Dismiss button
    Then the popup should close
    And the table should remain unchanged
    Examples:

      | Feature           | field1 | value1     | field2 | value2         | field3      | value3     | field4   | value4  |
      | "Przychody"       | date   | 2025-03-18 | source | Umowa zlecenie | amount      | 158,30     |          |         |
      | "Bieżące wydatki" | date   | 2025-05-01 | amount | 258369         | description | Mieszkanie | category | Lichwa  |
      | "Stałe wydatki"   | name   | Pralnia    | amount | 14             | dueDate     | 2024-02-19 | category | Zdrowie |