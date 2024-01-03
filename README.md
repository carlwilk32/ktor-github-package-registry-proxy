## GitHub Packages Repository Proxy

There are no doubts [GitHub Packages Repository (GPR)](https://github.com/features/packages) provides an easy and
straightforward way to publish Maven artifacts within GitHub ecosystem.

However,
GitHub [doesn't allow anonymous read](https://docs.github.com/en/packages/learn-github-packages/introduction-to-github-packages#authenticating-to-github-packages)
which makes consuming those packages somewhat awkward and requires user to provide explicit Personal Access
Token (PAT).

This project comes to help as it allows to proxy all GPR requests and resolve dependencies without the need to create
and
provide PAT.

### Personal Access Token

Follow the instructions
to [create a personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic)
and only set `read:packages` as a required permission.

Proxy will rely on this token to get access and read packages deployed under your profile.

> <picture>
>   <source media="(prefers-color-scheme: light)" srcset="https://github.com/Mqxx/GitHub-Markdown/blob/main/blockquotes/badge/light-theme/note.svg">
>   <img alt="Note" src="https://github.com/Mqxx/GitHub-Markdown/blob/main/blockquotes/badge/light-theme/note.svg">
> </picture><br>
>
> The same token can be used to access and read public packages from ANY other from GitHub Package Repositories.

### Local run

The following command will start proxy at your host machine on port 8080 by default

```shell
export GITHUB_USER=<YOUR_USERNAME>
export GITHUB_TOKEN=<ABOVE_TOKEN>
./gradlew run
```

#### Use local proxy from your build file

Edit your `build.gradle` and provide url to your local proxy

```gradle
maven {
    url 'http://0.0.0.0:8080'
    allowInsecureProtocol true
}
```

## Deploy

You can deploy this proxy to any Cloud Provider / Server / VPS able to run java runtime.

### Deployable jar

Run following command that will create executable *fatJar* that contains all the dependencies

```shell
./gradlew build
```

### Containerize

The easiest way to build a docker image is to use Ktor's bundled [jib](https://github.com/GoogleContainerTools/jib) tool

Just run the following command to build an image to a Docker daemon

```shell
./gradlew jibDockerBuild
```

or to your specific container registry (dockerHub, GCR, ECR, etc)

```shell
./gradlew jib --image=<IMAGE_NAME>
```

you can then run the container or deploy it to whatever service that is able to run Docker containers

```shell
docker run --env GITHUB_USER=user --env GITHUB_TOKEN=token -p 8080:8080 -d ktor-github-package-registry-proxy
```

### Deploy to fly.io

The easiest and **free** option to get your proxy up and running is to deploy to *fly.io* as they have
a [free tier](https://fly.io/docs/about/pricing/#free-allowances) which is enough for this kind of service.

0. Delete `fly.toml` in the root of this project.
1. Install `flyctl` command line tools for [your platform](https://fly.io/docs/hands-on/install-flyctl/).
2. Run following commands and follow onscreen instructions
```shell
fly auth login
fly launch --no-deploy
fly deploy
```
don't forget to change your compute instance to meet free tier allowance. 

For more details refer to official fly.io [documentation](https://fly.io/docs/languages-and-frameworks/dockerfile/).

That's it! Now you can use fly's app url to reference to your GPR proxy.

Enjoy!

