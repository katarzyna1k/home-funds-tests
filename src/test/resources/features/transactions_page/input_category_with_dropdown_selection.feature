Feature: Feature: Select a category from the dropdown in the Category input on the Transactions page

  Scenario Outline: User select one of the categories from dropdown
    Given the user opens the Transactions feature page
    And the user clicks the Add button in the subpage
    Then the user fills the following inputs:
      | field    | value    |
      | <field1> | <value1> |
      | <field2> | <value2> |
      | <field3> | <value3> |
    And the user clicks the dropdown arrow for <dropdownField>
    And the user selects category "<category>"
    And the user clicks the Submit button
    Then the user sees a new record with data field <value1> description field "<value2>" amount field <value3> and  category field "<category>"

    Examples:
      | field1 | value1     | field2      | value2     | field3 | value3 | dropdownField | category  |
      | date   | 2025-06-06 | description | testValue  | amount | 1      | category      | Jedzenie  |
      | date   | 2025-06-02 | description | testValue1 | amount | 2      | category      | Transport |
      | date   | 2025-06-03 | description | testValue2 | amount | 3      | category      | Rozrywka  |
      | date   | 2025-06-04 | description | testValue3 | amount | 4      | category      | Zakupy    |
      | date   | 2025-06-05 | description | testValue4 | amount | 5      | category      | Inne      |