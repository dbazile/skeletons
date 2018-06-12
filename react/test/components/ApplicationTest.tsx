import * as React from 'react'
import * as renderer from 'react-test-renderer'

import { Application } from '../../src/components/Application'

import classes from '../../src/components/Application.less'


describe('<Application/>', () => {
    let store: any

    beforeEach(() => {
        store = {
            fetchRevision: jest.fn(),
        }
    })

    it('has mocks for CSS modules', () => {
        expect(classes.subtitle).toEqual('subtitle')
        expect(classes.isRed).toEqual('isRed')
    })

    it('can render', () => {
        const component = renderer.create(
            <Application
                store={store}
            />,
        )
        expect(component.toJSON()).toMatchSnapshot()
        expect(store.fetchRevision).toHaveBeenCalledTimes(1)
    })
})
