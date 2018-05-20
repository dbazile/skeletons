from unittest import TestCase

from myproject.foo import lorem_ipsum


class FooTest(TestCase):

    def test_something(self):
        self.assertEqual(True, lorem_ipsum())
