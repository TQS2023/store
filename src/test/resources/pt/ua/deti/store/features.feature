Feature:
  Background:
    Given I start the browser

  Scenario: I want to see all the books
    Then I should see the list of books

  Scenario: I want to see the details of a book
    When I click on the book
    Then I should see the details of the book

  Scenario: I want to add a book to my cart
    When I click on the book
    And I click on the add to cart button
    And I close the modal
    And I click on the cart button
    Then I should see the book in my cart

  Scenario: I want to remove a book from my cart
    When I click on the book
    And I click on the add to cart button
    And I close the modal
    And I click on the cart button
    And I should see the book in my cart
    And I click on the remove from cart button
    Then I should not see the book in my cart