# Automaticly create a Project Folder to start on.
A utility wich create a folder at ~/repos (documents/repos windows) with the name passed as the first commandline argument. It also creates a git repository and put it on github.

#### This should going through the following steps:
1.  [x] Create a Folder at ~/repos (documents/repos for windows) with the first argument as it's name.
2.  [x] Create a README&#46;md file inside the folder.
3.  [x] Check for user&#46;name and user&#46;email. 
4.  [x] Initializes a git reposetory inside the folder.
5.  [x] Stage the README file.
6.  [x] Commit. \(commit -m "init commit"\)
7.  [ ] Check if a SSH key is present "ls \-al ~/\.ssh" (looking if there are files other then knwon hosts)

###### This will be followed if there are no SSH key files:
8.  [x] Create on github \(See extra steps\)
9.  [ ] Add the remote \(git remote add origin https://github.com/CodeIsNotEvil/CreateProjectFolder.git\)
10. [ ] Push it. \(git push -u origin master\)
11. [ ] Login. \(to finalize the push action\)
12. [ ] Open VsCode in the created folder.

###### This will be followed if there are SSH key files:
8.  [ ] Copy the fingerprint
9.  [x] Create on github \(See extra steps\)
10. [ ] Go to Settings and Select "SSH and PGP" Keys.
11. [ ] Look for a matching key fingerprint. \(need more details\) 
12. [ ] If there is a Matching Key close the browser. 
13. [ ] If there is non Matching key add it to the list and close the browser \(Multiple steps requiered\)
14. [ ] Add the remote \(git remote add origin git@github.com:CodeIsNotEvil/CreateProjectFolder.git\)
15. [ ] Push it. \(git push -u origin master\)
16. [ ] Open VsCode in the created folder.

###### These are the substeps of create on github
9.  [x] Open a Browser. \(Firefox gets implemented and tested first then Chrome\)
10. [x] Go to github.
11. [x] Login.
12. [x] Create a new public project.
13. [x] name it like the Folder.
