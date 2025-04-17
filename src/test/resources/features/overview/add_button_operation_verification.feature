Feature: Adding records to tables across feature pages

  Scenario Outline: User add record to the table, and the table size increases by one
    Given the user opens the <Feature> feature page
    When the user notes the initial data in the table
    And the user clicks the Add button in the subpage
    And the user fills the following inputs:
      | field    | value    |
      | <field1> | <value1> |
      | <field2> | <value2> |
      | <field3> | <value3> |
      | <field4> | <value4> |
    And the user clicks the Submit button
    Then the popup should close
    And the table size should increase by one
    Examples:

      | Feature           | field1 | value1     | field2 | value2         | field3      | value3     | field4   | value4  |
      | "Przychody"       | date   | 2024-03-18 | source | Umowa zlecenie | amount      | 158,30     |          |         |
      | "Bieżące wydatki" | date   | 2025-06-01 | amount | 25836          | description | Mieszkanie | category | Lichwa  |
      | "Stałe wydatki"   | name   | Pralnia    | amount | 14             | dueDate     | 2024-03-19 | category | Zdrowie |