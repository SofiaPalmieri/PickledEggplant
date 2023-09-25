@Application(Microsoft_Word)
Feature: Application

  Scenario Outline: Launch <application>
    When I click windows start button
    And search for <application>
    And launch <application>
    Then <application> launches
    Examples:
      | application|
      | "word"     |
