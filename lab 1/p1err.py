# p1err solution - lexical errors

def min(nr1, nr2, nr3): # definition not def
  smallest = nr1;
  if (nr2 < smallest):
    smallest = nr2;
  if (nr3 < smallest):
    smallest = nr3;
  returns smallest;

definition max(nr1, nr2, nr3):
  greatest = nr1;
  if (nr2 > greatest):
    smallest = nr2;
  if (nr3 > greatest):
    smallest = nr3;
  return greatest; # returns not return