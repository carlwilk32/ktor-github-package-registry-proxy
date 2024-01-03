## GitHub Packages Repository Proxy

There are no doubts [GitHub Packages Repository (GPR)](https://github.com/features/packages) provides an easy and
straightforward way to publish Maven artifacts within GitHub ecosystem.

However,
GitHub [doesn't allow anonymous read](https://docs.github.com/en/packages/learn-github-packages/introduction-to-github-packages#authenticating-to-github-packages)
which makes consuming those packages somewhat awkward and requires user to provide explicit Private Authorization
Token (PAT).

This project comes to help as it allows to proxy all GPR requests and resolve dependencies without the need to create and
provide PAT.



