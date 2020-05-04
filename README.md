# mejia-CMPT440

Final Project

Formal Languages and Computability CMPT440L

For this project, you will develop a version of the grep utility, in a language of your choice, called grepy​, that searches files for regular expression pattern matches and produces dot graph file output for the automata used in the matching computation.  

# Name Grepy
Example call (Java)  
java grepy.Grep [-n NFA-FILE] [-d DFA-FILE] REGEX FILE

# Description
Grepy should perform the following high-level steps.  
● Learn the alphabet from the input FILE.  
● Convert the regular expression REGEX to a NFA.  
● Convert the NFA to a DFA.  
● Use DFA computation to test each line of the file for accept/reject.  
● File lines are delimited by newline characters.  
● Accepted lines should be printed to standard output.  
● Output the NFA and/or DFA to the specified filenames in DOT language format.  
○ http://en.wikipedia.org/wiki/DOT_language  
○ http://www.graphviz.org/content/dot-language  

# Implementation Requirements
● You must create a private GitHub repository and add me “gildmi” as a collaborator. IF I can’t see it, I can’t grade it, which results in a zero.  
● Create GitHub issues for each of the requirements with appropriate tags.  
● Each issue must be closed with the appropriate commit that satisfies the requirements.  
● Only the master branch will be graded but you must use a dev branch and merge using
pull requests.  
●Use “good” GitHub etiquette including often commits, useful commit messages, and a well formed README. Take a look at the additional resources document in iLearn for more information.  
● Each line should be considered for acceptance in its entirety. This is equivalent to using grep with start (^) and end ($) anchors surrounding the pattern.  
● You may compute on the NFA if you prefer, but you still need to generate the DFA for DOT output.  
● Your program should work with or without the DOT output files specified.  
● Generate a few different test files to test with and include with your submission. This
should include at least a simple match, multiple lines match, and negative match.  
● Employ a static code analyzer, to ensure syntactic correctness, clean code, and help
understand your coding style compliance with existing standards. Again, use the
additional resources document for more information and links.  
● If you reach out to me about an issue you are having or a bug you have, you must write
up a GitHub issue for the defect using the following guidelines.  
○ What you are trying to do. Keep the Defect Summary concise.  
○ What you did. In description write clear steps to reproduce the problem.  
○ Where are you running. Describe the test environment in detail.  
○ Describe the specific error. Do not be too verbose, keep to the facts.  
○ Details matter. Attach snapshots and Logs.  
○ Automation is not a priority. Assign appropriate severity and priority.  
○ Contact me asap, not the day the hw is due! Multiple issues should not be  
grouped into a single defect entry.  
○ Close the bug with the commit you push to solve the issue.  
● It is highly encouraged to use a build mechanism such as Maven or Gradle (depends on your language of choice).  

# Extra Credit
1. In addition to computing string inclusion using the DFA, also implement the decision via a stack machine.  
2. Visualize your DFA and NFA as optional output of your program. You may use libraries.  
3. In addition to export the NFA and DFA to a DOT formatted file, allow the user to also
export the graphs to a picture file.  

# Submission
1. Please submit your final code, including any test data files, via committing, pushing, and merging into your private GitHub repository master branch.  
2. Please submit your GitHub link and an archive file (zip) of all code, any build files, test files, and compiled executables via iLearn.  

# Grading
Grading will be based on content, accuracy, communication, thoroughness, and adherence to instructions for up to 20% of your final grade. The following attributes proved the grading criteria.  
● 60% - Syntactic and functional correctness  
● 10% - Demonstrates “good” design principles (ease-of-use, accessibility, etc.)  
● 10% - “good” GitHub etiquette (Often commits, useful commit messages, well formed
README, ect.)  
● 10% - Exhibits “good” style (nested indentation, comments, etc.)  
● 5% - Uniqueness and/or excellence (discretionary points for the best solutions)  
● 5% - Test files (simple match, multiple lines match, negative match)  
● -1,000,000 points for a non-private GitHub repository or if I do not have access to it.  

# Implementation
NFA Creation Based On Thompson's Construction Algorithm  
Resources  
https://en.wikipedia.org/wiki/Thompson%27s_construction  
http://www.cs.may.ie/staff/jpower/Courses/Previous/parsing/node5.html  
https://www.youtube.com/watch?v=RYNN-tb9WxI  

DFA Creation Based On Matrix Manipulation  
NFA To DFA naming scheme is a little awkward as I could not come up with a better solution other than just using the already numbered nodes and adding them together in a string format