Feature: Managing inventory

  Scenario: Adding a product actually adds it to the inventory
    Given there are 4 sweaters in the inventory
    When I add 1 sweaters to the inventory
    Then the inventory contains 5 sweaters


  Scenario: Removing a product actually removes it
    Given there are 3 t-shirts in the inventory
    When I remove 1 t-shirts from the inventory
    Then the inventory contains 2 t-shirts

  Scenario: Refunding a client puts the item back in inventory
    Given there are 10 t-shirts in the inventory
    When I refund a client that bought a t-shirt
    Then the inventory contains 11 t-shirts