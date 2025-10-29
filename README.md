Name: Travel Expert System
Description: 
  A Java console-based expert system that recommends travel destinations based on
  user responses. Uses a binary decision tree to guide the user through yes/no
  questions and arrive at the most suitable travel recommendation.

Features:
  - Interactive question-based travel advisor
  - Uses a decision tree (binary tree structure)
  - Demonstrates recursion and traversal algorithms
  - Object-oriented design with modular Java classes
  - Easily extendable with new destinations or questions

Files:
  - TreeNode.java: Defines the structure of each node in the decision tree
  - DecisionTree.java: Builds and manages the question/answer tree
  - TravelExpertSystem.java: Main driver handling user interaction and logic

Usage:
  Steps:
    - Clone the repository:
      - git clone https://github.com/yourusername/travel-expert-system.git
      - cd travel-expert-system
    - Compile all Java files:
      - javac *.java
    - Run the program:
      - java TravelExpertSystem
  Example:
    Input:
      - Do you prefer warm weather? yes
      - Do you enjoy beaches? yes
      - Would you like cultural attractions? no
    Output:
      - Recommended destination: Bali, Indonesia

Concepts Used:
  - Binary decision trees
  - Recursion and traversal algorithms
  - Java object-oriented programming
  - Console-based user input and logic flow

Testing:
  - Each node and traversal path can be tested by simulating yes/no responses
  - Add new nodes easily to expand destination coverage

license: MIT License © 2025 Ishaan Solanki
