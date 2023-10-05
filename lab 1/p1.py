# p1 solution

definition min(nr1, nr2, nr3):
  smallest = nr1;
  if (nr2 < smallest):
    smallest = nr2;
  if (nr3 < smallest):
    smallest = nr3;
  returns smallest;

definition max(nr1, nr2, nr3):
  greatest = nr1;
  if (nr2 > greatest):
    greatest = nr2;
  if (nr3 > greatest):
    greatest = nr3;
  returns greatest;