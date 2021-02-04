# `cljs-todo-app`

> A practice todo app built with ClojureScript and Reagent

[View it here](https://dviramontes.github.io/cljs-todo-app/)

### Project Layout

```
{root}

...
```

### Requirements

- [node.js > 12 + npm](https://nodejs.org/en/)
- [Clojure](https://clojure.org/guides/getting_started)
- [Java SDK](https://adoptopenjdk.net/) (Version 8+, Hotspot)

#### Developement tools

- [rlwrap](https://github.com/hanslub42/rlwrap) `brew install rlwrap`
- [clj-kondo](https://github.com/clj-kondo/clj-kondo/blob/master/doc/install.md) `brew install borkdude/brew/clj-kondo`

### Setup

- `npm install`

### Development

- `npm run lint` # uses clj-kondo
- `npm run dev` # runs development server
- `npm run repl` # runs browser-repl
- `npm run format` # runs prettier formatter
- `npm run build` # builds production build
- `npm run clean` # deletes public/js dir

### Production

- `npm run deploy` # runs clean, build, then deploys site to github pages

### Credits

- [I learned about making svg bar charts via this codepen tutorial (<3 codepen) by Robin Rendle](https://css-tricks.com/how-to-make-charts-with-svg/)
- [I learned about making svg pie charts via this super great tutorial by Kasey Bonifacio](https://seesparkbox.com/foundry/how_to_code_an_SVG_pie_chart)
