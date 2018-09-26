The Poller Express
===============

This project is for our CS 340 class. If you're here you already know what this project is, though, so lets head on to some help for using git on our project.

Git Commands
------------------

### Pulling the Project

Don't currently have the project on your laptop? This is how you get it there.

Navigate into the folder you would like the `thepollerexpress` folder to be placed into. Run the following command:

`git clone https://github.com/JOTworks/ttr.git`

The folder will be created in the directory. Navigate into the folder, and ta-da, you are in the project.

### Pulling Others' Changes

Someone else has added their changes to the git repository, and you need the code on your computer so you can use it in coding your portion of the project. If you have already cloned the git repository and some version of the project exists on your computer, you use the `git pull` command. If you're not getting fancy with branches, it's that easy. All the changes that have been pushed to the repository are now on your computer.

### Pushing Your Changes

You've been coding for a bit, adding some features, fixing some bugs. Now you want to push your changes onto the git repository so everyone else can see your lovely code. (Please do this often, to make it easier on all of us.) Essentially, you bundle up your changes into something called a commit and push that to the online repository. There are a couple of steps to do this.

**First:** Do the `git pull` command. You don't know who else has pushed their code to the repository since the last time you pulled, and things get really awkward when you accidentally erase someone else's changes because you forgot to pull first.

**Second:** Okay, this step isn't technically necessary, but it helps me visualize what exactly I'm pushing to the repository easier. Run `git status` and it will show you what files you have changed, and which ones are staged or unstaged (which I will explain in the next step). I run this literally every time I push, but to each their own.

**Third:** Add your file changes to the staging area, so they can be committed in the next step. Staging changes is done through the `git add` command, which is the first command we've gone over that actually requires some parameters. You can add specific files to the staging area by using the pattern `git add "filename"`, or you can add all of your changes at once by using `git add -A`.

**Fourth:** Commit your changes. This packages up all the changes you made nice and neat and makes it a lot easier to undo changes if that becomes necessary, *and* it lets you add a message to that group of changes you made. The command is `git commit -m "message"`, and it will package all of the changes that have been added to the staging area with the `git add` command from last step. Keep in mind those changes are no longer in the staging area, they're in the commit. **NOTE:** Please, please do NOT set the commit message as "commit". The purpose of the message is to be able to track what is happening to the code. If you fixed a bug, say what you fixed. If you added a feature, say what you added. If you only did part of what you're planning, say what part you did. Messages are important.

**Fifth:** Last step---push your commit so your changes are actually on the git repository. This command is simply `git push` and will push all of your commits (if a change is not in a commit it won't get pushed to the git repository by this command).

Ta-da! You have pushed your changes to the git repository.

### Branches

This is a lot more complicated than just pulling and pushing changes, so I'm going to work on this later. (Like, tomorrow or something.)