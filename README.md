# Haratres F.I.T Internship Assessment

Two console programs written in plain Java, without frameworks.

| Task | Description | Status |
|---|---|---|
| 1 | Character counter (`FindMyChar`) | Implemented (`com.murat.Main`) |
| 2 | Product management, sorting and cart | Implemented (`com.murat.Main`) |

---

## Task 1: Character counter

Counts how many times a chosen character appears in a sentence, with or without case sensitivity.

**Flow:** maximum length → sentence (re-asked while it exceeds the limit) → case sensitivity → character to analyze → result → optionally analyze another character in the same sentence.

### Requirement coverage

| Requirement | Behavior |
|---|---|
| Ask for the maximum number of characters | Positive integer required, anything else is rejected and asked again |
| Sentence within the limit, otherwise warn and re-ask | Over-limit and blank sentences are rejected and asked again |
| Ask for the case-sensitivity preference before analysis, reject invalid answers | Accepts `y`, `yes`, `n`, `no` in any case, everything else shows an error and asks again |
| Ask for a character, error if none is given | Exactly one character is required; empty or longer input shows an error and asks again |
| Count occurrences according to the preference | Explicit character-by-character loop, no counting library |
| _Extra_ | The user can analyze several characters in the same sentence without restarting |

### Error handling

- Every validation rule has its own checked exception, named after the rule: `LimitMustBeGreaterThanZeroException`, `InputMustNotBeBlankException`, `InputLengthMustBeLessThanOrEqualToLimitException`, `InputNotEqualYesOrNoException`, `ForAnalyzeNeedOneCharException`.
- All of them extend a common `InvalidInputException` and carry the user-facing message. Errors are displayed through one method (`printError`), so the way errors are presented can be changed in a single place. This is the same idea as a centralized exception handler, done without a framework.
- Non-numeric limits are handled through `NumberFormatException` from `Integer.parseInt`.

### Design notes

- Small single-purpose methods, one per prompt and one per validation rule.
- Case-insensitive comparison uses `Character.toLowerCase(char)`, which does not depend on the system locale, so the program behaves the same on a Turkish or English machine.

### Assumptions and limitations

- Input is trimmed. A single space therefore cannot be analyzed.
- Characters outside the Basic Multilingual Plane (for example emoji) are not supported.
- Prompts and messages are in English. Accepted yes/no answers are `y`, `yes`, `n`, `no`.

---

## Task 2: Product management and cart

A console program that reads a list of products, sorts them by a chosen criterion, lets the user fill a cart, and calculates the cart total with a "next item" discount.

**Flow:** number of products → product details (name, price, stock, rating) → sort criterion → sort order → sorted list → cart (product name + quantity, repeated) → cart summary with discount and total.

### Project structure

```
com.murat
├── Main.java                 console flow: reads input, validates it, prints results
└── domain
    ├── Product.java          name, unit price (BigDecimal), stock, rating
    ├── CartItem.java         a product and its quantity (one line of the cart)
    ├── Cart.java             cart lines, stock-aware add, insertion order preserved
    └── DiscountCalculator.java   discount rule and cart total
```

### Requirement coverage

| Requirement | Behavior |
|---|---|
| Ask how many products, more than one is required | At least 2 products; anything else shows a warning and asks again |
| Name, price, stock and rating for each product | Each field has its own loop, so only the invalid field is asked again |
| Price between 1 and 100 | Both ends inclusive, otherwise warning and asked again |
| Stock at least 1 | Otherwise warning and asked again |
| Name at most 20 characters | Empty names and names longer than 20 characters are rejected |
| Rating out of 5 | Decimal between 1 and 5, both ends inclusive |
| Sort by name / stock / rating | Criterion is case-insensitive; invalid input asks again |
| Ascending or descending | Order is case-insensitive; invalid input asks again |
| Ask whether to add products to the cart | `Yes`/`No` in any case; `No` on the first question ends with an empty cart |
| Product name and quantity, stock check | Unknown product → warning; quantity above the remaining stock → warning and only the quantity is asked again |
| At least two products in the cart | Answering `No` to "add another product?" with fewer than 2 different products shows a warning and continues |
| Discount and cart total | See the discount rule below |

### Discount rule

Cart lines are processed in the order they were added. For each line `A` that has a next line `B`:

- if `A`'s unit price is **greater** than `B`'s unit price, the discount of line `A` is `B`'s unit price × `A`'s quantity;
- otherwise there is no discount;
- the last line never gets a discount;
- comparisons always use the original unit prices.

The calculation is repeated from the whole cart after every addition. Adding more of an existing product therefore updates its discount automatically.



**Example (chain):** A 10 × 1, B 6 × 2, C 8 × 1, D 2 × 3

| Line | Discount | Line total |
|---|---|---|
| A | 6 × 1 = 6.00 | 10.00 − 6.00 = 4.00 |
| B | none (6 < 8) | 12.00 |
| C | 2 × 1 = 2.00 | 8.00 − 2.00 = 6.00 |
| D | none (last line) | 6.00 |

Total discount **8.00**, cart total **28.00**.

### Example run 

```
How many different products do you want to add? 2
Product name: Kalem
Unit price: 1.50
Stock quantity: 100
Rating: 4.5
Product name: Defter
Unit price: 3.00
Stock quantity: 50
Rating: 4.7
Which criterion do you want to sort products? (name/stock/rating): rating
Ascending or descending? (ascending/descending): descending
Sorted Products:
Defter - Price: 3.00, Stock: 50, Rating: 4.7
Kalem - Price: 1.50, Stock: 100, Rating: 4.5
Do you want to add products to your cart? (Yes/No): yes
Name of the product you want to add: Defter
Quantity to add: 2
Defter added to your cart.
Do you want to add another product? (Yes/No): yes
Name of the product you want to add: Kalem
Quantity to add: 2
Kalem added to your cart.
Discount applied on Defter because it is more expensive than Kalem. Discount: 3.00 (6.00 -> 3.00)
Do you want to add another product? (Yes/No): no
Your cart:
Defter - Quantity: 2, Total Price: 3.00
Kalem - Quantity: 2, Total Price: 3.00
Total Discount: 3.00
Cart Total: 6.00
```

### Design notes

- **Money uses `BigDecimal`.** No floating-point rounding errors in prices, discounts or totals. Money is printed with `setScale(2, RoundingMode.HALF_UP).toPlainString()`, so the output is always `3.00` and never depends on the system locale (no `3,00` on a Turkish machine).
- **Stock is checked against the cart, not decremented.** The product's stock never changes. The remaining stock is `stock − quantity already in the cart`, so adding the same product twice cannot exceed the stock in total.
- **One line per product.** Adding an existing product increases the quantity of its line; the original insertion order is kept, which the discount rule depends on.
- **The original product list is never modified by sorting.** Sorting works on a copy. Ties are broken by name (ascending), even when the main order is descending.
- **Input validation is local to each field.** Each prompt is its own `while` loop that keeps asking until the value is valid, so a wrong value only repeats that one question. Task 2 deliberately does not use custom exceptions: unlike Task 1, there is a single flow and every rule is a one-line check inside its loop, so exceptions would only add classes without adding clarity. The only exception handled is `NumberFormatException` from number parsing.
- **Separation of concerns.** `Cart` and `Product` contain no printing or input code. `Main` owns all console interaction. `DiscountCalculator` holds the discount rule in one place (`giveDiscount`), so changing the rule means changing one method. Both the per-step explanation and the final summary use the same rule and cannot drift apart.

### Assumptions and limitations

- (more than one different product) is read as **at least 2 different products**, both for the product list and for the cart. Adding the same product twice counts as one product.
- Price and rating limits include both ends (1 and 100, 1 and 5). Stock must be at least 1.
- The discount formula follows the specification ("a discount equal to the unit cost of the second product") and is applied per unit of the first line, so a line with quantity 3 gets 3 × the next unit price. The specification example gives the same result under either reading; Example 2 above shows how this implementation behaves on a longer chain.
- Product names are unique (case-insensitive), because the cart finds products by name. Name lookup in the cart is also case-insensitive and trimmed.
- The decimal separator for price and rating is a dot (`1.50`).
- All messages are in English. Accepted yes/no answers are `Yes` and `No` (any case) for this task.
- The program is single-threaded and a single `Scanner` on standard input is shared by all methods.

### Verification

Checked by running the program and by hand calculation:

| Scenario | Result |
|---|---|
| Both specification examples (Example above) | 6.00 / 3.00 and 28.00 / 8.00 |
| Sort by name, stock and rating in both orders, equal values | Correct order, ties by name |
| Unknown product name | Warning, only the name is asked again |
| Quantity above remaining stock | Warning with the maximum allowed, only the quantity is asked again |
| Quantity `0`, negative, non-numeric | Warning, quantity asked again |
| Same product added several times | One cart line, quantities add up, stock limit applies to the total |
| Product with no stock left | "No stock left" message, name asked again |
| Invalid yes/no answer (`y`, text) | "Please enter a valid answer." |
| Re-adding a product after later lines exist | Discount of its line is recalculated |
| Long chain with repeated products (4 products, 12.00 discount, 18.00 total) | Matches the hand calculation |