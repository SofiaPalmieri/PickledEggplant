@Application(Microsoft_Word)
Feature: Application

  Scenario Outline: Check out check in Word <document>
    When I open iManage
    And I open a <document> from iManage
    And <document> is checked out
    And Close word
    Then <document> is checked in
    Examples:
      | document |
      | "Document for searches"|
