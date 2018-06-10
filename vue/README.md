# new-vue-app

> Skeleton for VueJS apps.


## Running locally for development

```bash
./scripts/develop.sh
```


## Building

### Compiling all assets

```bash
./scripts/compile.sh
```

### Packaging for deployment

```bash
./scripts/package.sh
```


## Linting source code

```bash
./scripts/lint.sh

# Automatically fix certain linter errors
./scripts/lint.sh --fix
```


## Running unit tests

```bash
./scripts/test.sh

# Run in watch mode
./scripts/test.sh --watchAll
```


## Environment Variables

| Variable | Description |
|----------|-------------|
| `NODE_ENV` | If `production`, bundle will be minified and stripped of development helpers/scaffolding code (defaults to `development`). |
| `API_PROXY` | Overrides the default hostname webpack-dev-server proxies API requests to during local development. |
| `COMPILER_TARGET` | Overrides the ECMAScript version of code TypeScript emits (defaults to `es7` in dev, `es6` in prod). |
