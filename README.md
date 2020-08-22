# Automaticly create a Project Folder to start on.
A utility wich create a folder at ~/Repos with the name passed as the first commandline argument. It also creates a git repository and put it on github.

#### This should going through the following steps:
1.  [ ] Create a Folder at ~/Repos with the first argument as it's name.
2.  [ ] Create a README&#46;md file inside the folder.
3.  [ ] Check for user&#46;name and user&#46;email (for now  Only existens) 
4.  [ ] Initializes a git reposetory inside the folder.
5.  [ ] Stage the all files.
6.  [ ] Commit. \(commit -m "init commit"\)
7.  [ ] Check if a SSH key is present "ls \-al ~/\.ssh" (looking if there are files other then knwon hosts)

###### This will be followed if there are no SSH key files:
8.  [ ] Create on github \(See extra steps\)
9.  [ ] Add the remote \(git remote add origin https://github.com/CodeIsNotEvil/CreateProjectFolder.git\)
10. [ ] Push it. \(git push -u origin master\)
11. [ ] Login. \(to finalize the push action\)
12. [ ] Open VsCode in the created folder.

###### This will be followed if there are SSH key files:
8.  [ ] Copy the fingerprint
9.  [ ] Create on github \(See extra steps\)
10. [ ] Go to Settings and Select "SSH and PGP" Keys.
11. [ ] Look for a matching key fingerprint. \(need more details\) 
12. [ ] If there is a Matching Key close the browser. 
13. [ ] If there is non Matching key add it to the list and close the browser \(Multiple steps requiered\)
14. [ ] Add the remote \(git remote add origin git@github.com:CodeIsNotEvil/CreateProjectFolder.git\)
15. [ ] Push it. \(git push -u origin master\)
16. [ ] Open VsCode in the created folder.

###### These are the substeps of create on github
9.  [ ] Open a Browser. \(Firefox gets implemented and tested first then Chrome\)
10. [ ] Go to github.
11. [ ] Login.
12. [ ] Create a new public project.
13. [ ] name it like the Folder.
