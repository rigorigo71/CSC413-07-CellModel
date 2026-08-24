<!--
  CSC 413 — pull request template; fill in every section before you request
  review.

  HOW THIS PULL REQUEST (PR) IS GRADED — two things, separately:
    • your code's correctness — acceptance tests (gates the merge);
    • the quality of THIS write-up and your git history — reviewed after the
      deadline.

  AI POLICY FOR THIS SLICE: AI-FREE.  You may use AI to UNDERSTAND (explain an
  error, a concept), but the code you submit must be your own — do not paste
  AI-generated code.  There is deliberately no "AI assistance" section on this
  slice; later slices open this up, and the template will ask you to disclose it.

  PR TITLE: concise and specific — "implement Ref.parse and toA1; add round-trip
  tests".
-->

## what changed

<!-- one or two sentences, in your own words: what did you implement? -->

## how it works

<!-- explain your parse/toA1; call out the decision that mattered: why parse
     returns null on malformed input instead of throwing; write from your own
     understanding, no tool open -->

## how I tested it

<!-- name the command (./gradlew test) and the cases — round-trip, malformed,
     multi-letter — and paste the result; "it works" with no evidence doesn't
     count -->

<!-- BEFORE YOU OPEN THIS PR, CHECK: commits tell a story; diff touches only
     Ref.java + tests; no build output/IDE files; no edits to the given
     Value/Content/error.Type -->
