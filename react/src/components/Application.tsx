import * as React from 'react'
import * as $ from 'classnames'
import { inject, observer } from 'mobx-react'
import { BrowserRouter, Route, Switch, Link } from 'react-router-dom'

import styles from './Application.less'

import { MercuryAnimation, DiscAnimation } from './ui/ActivityIndicator'
import { Button } from './ui/Button'
import { Checkbox } from './ui/Checkbox'
import { Label } from './ui/Label'
import { Menu, MenuDivider, MenuHeader, MenuItem } from './ui/Menu'
import { Pager } from './ui/Pager'
import { ProgressBar } from './ui/ProgressBar'
import { Radio } from './ui/Radio'
import { SegmentedControl } from './ui/SegmentedControl'
import { Slider } from './ui/Slider'
import { Tab, Tabs } from './ui/Tabs'
import { TextField } from './ui/TextField'
import { IStore } from '../store'


const Home = () => (
    <main>
        <h1>new-webapp</h1>
        <p>Hello World</p>
    </main>
)


const NotFound = (props) => (
    <main>
        <h1>Not Found!</h1>
        <pre>{JSON.stringify(props, null, 4)}</pre>
    </main>
)


const UISandbox = () => (
    <main>
        <h1>UI Sandbox</h1>
        <ul className={styles.sandbox}>
            <li>
                <h2>&lt;ActivityIndicator /&gt;</h2>

                <div style={{ padding: '15px' }}>
                    <MercuryAnimation />
                </div>

                <div style={{ padding: '15px' }}>
                    <DiscAnimation />
                </div>
            </li>

            <li>
                <h2>&lt;Button /&gt;</h2>

                <Button
                    icon={<svg viewBox="0 0 10 10" style={{width: '1em', height: '1em'}}><circle cx="5" cy="5" r="3" fill="red" /></svg>}
                />
                {' '}

                <Button
                    icon={<svg viewBox="0 0 10 10" style={{width: '1em', height: '1em'}}><path fill="green" d="M2.87867966,5 L5,7.12132034 L7.12132034,5 L5,2.87867966 L2.87867966,5 Z"/></svg>}
                    label="wat"
                />
                {' '}

                <Button
                    primary
                    label="Do it"
                />
                {' '}

                <Button
                    danger
                    label="Oh no"
                />
            </li>

            <li>
                <h2>&lt;Checkbox /&gt;</h2>

                <label>
                    <Checkbox checked={true}/> Checked
                </label>
                {' '}

                <label>
                    <Checkbox checked={false}/> Unchecked
                </label>
                {' '}

                <label>
                    <Checkbox checked={null}/> Indeterminate
                </label>
                <br/>

                <label>
                    <Checkbox disabled checked={true}/> Checked (disabled)
                </label>
                {' '}

                <label>
                    <Checkbox disabled checked={false}/> Unchecked (disabled)
                </label>
                {' '}

                <label>
                    <Checkbox disabled checked={null}/> Indeterminate (disabled)
                </label>
            </li>

            <li>
                <h2>&lt;Label /&gt;</h2>

                <Label
                    text="Regular Label"
                />
                <br/>

                <Label
                    text="Label with custom tooltip"
                    tooltip="Hey I'm a custom tooltip"
                />
                <br/>

                <Label
                    size="large"
                    text="Large Label"
                />
                <br/>

                <Label
                    size="small"
                    text="Small Label"
                />
            </li>

            <li>
                <h2>&lt;Menu /&gt;</h2>

                <Menu>
                    <MenuHeader
                        label="With Icons"
                    />
                    <MenuItem
                        icon={<svg viewBox="0 0 10 10" style={{width: '1em', height: '1em'}}><path fill="green" d="M2.87867966,5 L5,7.12132034 L7.12132034,5 L5,2.87867966 L2.87867966,5 Z"/></svg>}
                        label="Lorem"
                    />
                    <MenuItem
                        icon={<svg viewBox="0 0 10 10" style={{width: '1em', height: '1em'}}><path fill="green" d="M2.87867966,5 L5,7.12132034 L7.12132034,5 L5,2.87867966 L2.87867966,5 Z"/></svg>}
                        label="Ipsum"
                    />
                    <MenuItem
                        icon={<svg viewBox="0 0 10 10" style={{width: '1em', height: '1em'}}><path fill="green" d="M2.87867966,5 L5,7.12132034 L7.12132034,5 L5,2.87867966 L2.87867966,5 Z"/></svg>}
                        label="Dolor"
                    />
                    <MenuDivider />
                    <MenuHeader
                        label="Without Icons"
                    />
                    <MenuItem
                        label="Something selectable"
                    />
                    <MenuItem
                        selected
                        label="Something selected"
                    />
                    <MenuItem
                        disabled
                        label="Something disabled"
                    />
                    <MenuDivider />
                    <MenuHeader
                        label="Nested Things"
                    />
                    <MenuItem
                        icon={<svg viewBox="0 0 10 10" style={{width: '1em', height: '1em'}}><circle cx="5" cy="5" r="3" fill="red" /></svg>}
                        label="Nest"
                    >
                        <MenuItem label="Alpha"/>
                        <MenuItem label="Bravo"/>
                        <MenuItem label="Neeeeeest">
                            <MenuItem label="Charlie"/>
                            <MenuItem label="Delta"/>
                        </MenuItem>
                        <MenuItem label="Deep">
                            <MenuItem label="Nesting">
                                <MenuItem label="Can">
                                    <MenuItem label="Be">
                                        <MenuItem label="Very">
                                            <MenuItem label="Annoying"/>
                                        </MenuItem>
                                    </MenuItem>
                                </MenuItem>
                            </MenuItem>
                        </MenuItem>
                    </MenuItem>
                </Menu>
            </li>

            <li>
                <h2>&lt;Pager&gt;</h2>

                <Pager
                    value={1}
                    size={10}
                    totalCount={90}
                />
                <br/>

                <Pager
                    value={9}
                    size={10}
                    totalCount={90}
                />
                <br/>

                <Pager
                    value={5}
                    size={10}
                    totalCount={90}
                />
                <br/>

                <Pager
                    disabled
                    value={5}
                    size={10}
                    totalCount={90}
                />
            </li>

            <li>
                <h2>&lt;ProgressBar &gt;</h2>

                <ProgressBar value={25} />
                <br/>

                <ProgressBar value={50} />
                <br/>

                <ProgressBar value={75} />
                <br/>

                <ProgressBar />
                <br/>

                <ProgressBar value={25}>
                    Lorem Ipsum Dolor
                </ProgressBar>
                <br/>

                <ProgressBar value={50}>
                    Lorem Ipsum Dolor
                </ProgressBar>
                <br/>

                <ProgressBar value={75}>
                    Lorem Ipsum Dolor
                </ProgressBar>
                <br/>

                <ProgressBar>
                    Lorem Ipsum Dolor
                </ProgressBar>
            </li>

            <li>
                <h2>&lt;Radio /&gt;</h2>

                <label>
                    <Radio checked={true} /> Checked
                </label>
                {' '}

                <label>
                    <Radio checked={false} /> Unchecked
                </label>
                {' '}

                <label>
                    <Radio checked={null} /> Indeterminate
                </label>
                <br/>

                <label>
                    <Radio disabled checked={true} /> Checked (Disabled)
                </label>
                {' '}

                <label>
                    <Radio disabled checked={false} /> Unchecked (Disabled)
                </label>
                {' '}

                <label>
                    <Radio disabled checked={null} /> Indeterminate (Disabled)
                </label>
            </li>

            <li>
                <h2>&lt;SegmentedControl /&gt;</h2>

                <SegmentedControl
                    value={null}
                    items={[
                        {value: 'alpha', label: 'Alpha'},
                        {value: 'bravo', label: 'Bravo'},
                        {value: 'charlie', label: 'Charlie'},
                    ]}
                />
                <br/>

                <SegmentedControl
                    value="bravo"
                    items={[
                        {value: 'alpha', label: 'Alpha'},
                        {value: 'bravo', label: 'Bravo'},
                        {value: 'charlie', label: 'Charlie'},
                    ]}
                />
                <br/>

                <SegmentedControl
                    value="charlie"
                    items={[
                        {value: 'alpha', label: 'Alpha'},
                        {value: 'bravo', label: 'Bravo'},
                        {value: 'charlie', label: 'Charlie'},
                    ]}
                />
                <br/>

                <SegmentedControl
                    disabled
                    value="charlie"
                    items={[
                        {value: 'alpha', label: 'Alpha'},
                        {value: 'bravo', label: 'Bravo'},
                        {value: 'charlie', label: 'Charlie'},
                    ]}
                />
            </li>

            <li>
                <h2>&lt;Slider /&gt;</h2>

                <Slider
                    min={0}
                    max={10}
                    value={0}
                    step={0.05}
                />
                <br/>

                <Slider
                    min={0}
                    max={10}
                    value={5}
                />
                <br/>

                <Slider
                    min={0}
                    max={10}
                    value={10}
                />
                <br/>

                <Slider
                    disabled
                    min={0}
                    max={10}
                    value={5}
                />
            </li>

            <li>
                <h2>&lt;Tabs /&gt;</h2>

                <Tabs value="charlie">
                    <Tab value="alpha" label="Alpha">Now viewing <strong>Alpha</strong></Tab>
                    <Tab value="bravo" label="Bravo">Now viewing <strong>Bravo</strong></Tab>
                    <Tab value="charlie" label="Charlie">Now viewing <strong>Charlie</strong></Tab>
                    <Tab value="delta" label="Delta">Now viewing <strong>Delta</strong></Tab>
                </Tabs>
            </li>

            <li>
                <h2>&lt;TextField /&gt;</h2>

                <TextField
                    placeholder="Enter some text"
                    value="Lorem Ipsum"
                />
                {' '}
                <TextField
                    placeholder="Enter some text"
                    value=""
                />
                <br/>
                <br/>

                <TextField
                    multiline
                    placeholder="Enter some text"
                    value="Lorem Ipsum&#10;&#10;Dolor"
                />
                {' '}
                <TextField
                    multiline
                    placeholder="Enter some text"
                    value=""
                />
                <br/>
                <br/>

                <TextField
                    disabled
                    value="Lorem Ipsum"
                />
                {' '}
                <TextField
                    disabled
                    placeholder="Enter some text"
                    value=""
                />

                <br/>
                <br/>

                <TextField
                    disabled
                    multiline
                    value="Lorem Ipsum"
                />
                {' '}
                <TextField
                    disabled
                    multiline
                    placeholder="Enter some text"
                    value=""
                />
            </li>
        </ul>
    </main>
)


@inject('store')
@observer
export class Application extends React.Component<IProps, {}> {
    componentDidMount() {
        this.injected.store.fetchRevision()
    }

    private get injected() {
        return this.props as IPropsInjected
    }

    render() {
        return (
            <BrowserRouter>
                <main className={$({
                    [styles.root]: true,
                    [styles.isRed]: this.injected.store.isRed,
                    'some-global-thing': true,
                })}>
                    <header>
                        <Link to="/">Home</Link>
                        {' | '}
                        <Link to="/sandbox">UI Sandbox</Link>
                        {' | '}
                        <Link to={`/noooooooooooo`}>¯\_(ツ)_/¯</Link>
                    </header>

                    <Switch>
                        <Route exact path="/" component={Home}/>
                        <Route exact path="/sandbox" component={UISandbox}/>
                        <Route component={NotFound}/>
                    </Switch>

                    <footer>
                        <pre>this.injected.store = {JSON.stringify(this.injected.store, null, 4)}</pre>
                        <Button
                            label="Clicky"
                            onClick={this.onToggleClick}
                        />
                    </footer>
                </main>
            </BrowserRouter>
        )
    }

    private onToggleClick = () => {
        this.injected.store.toggleRed()
    }
}


/*
 * Types
 */

interface IProps {
    className?: any
}


interface IPropsInjected extends IProps {
    store: IStore
}
