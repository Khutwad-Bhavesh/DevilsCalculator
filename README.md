# 😈 Devil's Calculator

A prankster Java Swing calculator that flips a coin on **every button press**.
- 🟡 **HEADS** → works normally
- 🔴 **TAILS** → chaos: deletes a digit, full reset, or roasts you

---

## 📁 Project Structure
```
DevilsCalculator/
└── src/
    ├── Main.java
    ├── core/
    │   └── Calculator.java       ← math logic
    ├── ui/
    │   ├── CalculatorFrame.java  ← main window
    │   └── CoinFlipDialog.java   ← animated coin popup
    └── utils/
        ├── ChaosEngine.java      ← coin flip + chaos picker
        └── RoastMessages.java    ← roast message bank
```

---

## ▶️ How to Compile & Run

### In VS Code Terminal:
```bash
# From inside the DevilsCalculator/ folder:

# 1. Compile
javac -d out -sourcepath src src/Main.java

# 2. Run
java -cp out Main
```

Make sure you have **JDK** installed (not just JRE).
Check with: `javac -version`

---

## 🎮 Controls
Standard calculator. Every button press triggers a secret coin flip popup — brace yourself 😈
