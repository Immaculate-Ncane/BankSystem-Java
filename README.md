# BankSystem-Java

A console banking simulation written in Java. Individual coursework project.

> **Demo only.** All names, account numbers and PINs are made up. This is a learning project, not a real banking system, and it is not connected to any bank.

## What it does
- **Login** with a PIN (3 attempts), or **register** a new account with a username and a 4-digit PIN
- **Deposit** money into your account
- **Withdraw** money, with a check that you have enough balance
- **Transfer** to another account (10-digit account number, PIN to confirm) or to your own savings
- **Send cash** between R40 and R3000, with a 4-digit secret code for the recipient and a PIN to confirm
- Shows the account holder, account number and balance after login

## How to run
1. Open the project in NetBeans (or any Java IDE).
2. Run `BankSystem.java`.
3. To log in with the demo account, choose option 1 and use PIN `1234`.
4. Or choose option 2 to register a new account.

Requires Java 8 or newer. No libraries needed.

## Input checks
- Menu choices, amounts and PINs are re-asked until valid, so typing letters does not crash the program
- Amounts must be more than 0
- Withdrawals, transfers and cash sends cannot be more than the balance
- Account numbers must be exactly 10 digits, and you cannot transfer to your own account
- PINs must be exactly 4 digits and are stored as text, so a leading zero is kept

## Scenarios tested
- Wrong PIN, then correct PIN, and three wrong PINs in a row
- Deposit, withdrawal and a withdrawal larger than the balance
- Transfer to another account, to savings, and to your own account
- Cash send below the minimum and a valid cash send
- Registering a new account, including a PIN that is too short
- Letters, negative numbers and blank input where numbers are expected

## Known limitations
- Everything is kept in memory, so the data is lost when the program closes
- There is only one account at a time, and transfers do not credit a second account
- The PIN is stored in plain text, which is fine for a classroom demo but would not be acceptable in a real system
- There is no transaction history that survives between sessions

## Possible improvements
- Save accounts and transactions to a file or a database
- Support several accounts that can really send money to each other
- Hash the PIN instead of storing it as text
- Add a graphical interface with Java Swing

## Built with
Java (console application, `Scanner` for input)

## Author
Sthembele Immaculate Ncane
