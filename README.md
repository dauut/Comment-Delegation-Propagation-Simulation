### Comment Delegation Propagation Simulation

*This simulation developed for an academic journal which is in review*

In this simulation, we aimed to find out the best way to handle comments in a decentralized social network application 
while a user goes offline. During this offline time, the user gives delegation to a friend for handling comments below 
posts, and this delegation goes on until user come back or one of the previously delegated friends come back. We 
evaluated following three delegations heuristics along with an ideal scenario where an oracle tells which of the 
currently online friends will remain online the most;

1. a random friend
2. friend who has recently been online the most
3. friend who has recently been online the most while the user if offline (i.e., most disjointly online).

We simulated these scenarios using the online timing of each user in the circle of Facebook volunteers [data](https://ieeexplore.ieee.org/document/7365914). We assumed the online timing of oneof  the  volunteer’s  friends  as  the  online  timing  of  the  main  user  as  long  asthe  friend  appeared  online  for  at  least  a minute.   We  performed  a  timedsimulation  of  when  the  user  goes  offline,  for  how  long  the  user  is offline,and the delegation chain in the user’s absence.  We recorded for how long aparticular chain length is observed while the user is offline.

![Results](img/results.png)

Figure 12 presents the chain length probability of each simulation case of
the 2,266 timing samples from 16 users’ friend circles where probabilities are
independently ranked for clarity. We determined the most disjointly online
and most online persons based on the 5 previous day’s data assuming a circular time to use on the first five day’s calculations.
<p align="center">
  <img width="460" height="300" src="https://github.com/dauut/Comment-Delegation-Propagation-Simulation/blob/master/img/avg_results2.png?raw=true">
</p>
We also analyzed the average likelihood of each chain length for all
data sets. Figure 13 shows the performance of each approach in linear and
logarithmic scales. The offline probability of each friend – 74% and the
likelihood of having no online friend – 7.3% are the same for all models. We
observe on the average chain length of 1 is achieved by ideal with 61% of
cases and both most online and most disjoint achieve it with 51%. However,
random delegation can achieve minimal chains with 29% and performs much
worse than others.